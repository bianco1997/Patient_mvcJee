package com.example.patient_mvc;

import com.example.patient_mvc.entities.Patient;
import com.example.patient_mvc.repositories.PatientRepository;
import com.example.patient_mvc.securite.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }
    @Bean //au demarage cree moi un objet de type et tu le place dans ton contectext et si vous voulez le reeutiliser utiliser @autowired
    PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }
    @Bean //au demarage excute moi cette fonction
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {

            patientRepository.save(new Patient(null, "ayman", "tst", new Date(), false, 1200));
            patientRepository.save(new Patient(null, "ismail", "tst", new Date(), false, 1120));
            patientRepository.save(new Patient(null, "hamid", "tst", new Date(), false, 1220));
            patientRepository.save(new Patient(null, "mouad", "tst", new Date(), false, 1240));
            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });

        };
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("ayman","1234","1234");
            securityService.saveNewUser("nabil","1234","1234");
            securityService.saveNewUser("mouad","1234","1234");
            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");
            securityService.addRoleToUser("ayman","ADMIN");
            securityService.addRoleToUser("ayman","USER");
            securityService.addRoleToUser("nabil","USER");
            securityService.addRoleToUser("mouad","USER");
        };
    }

}
