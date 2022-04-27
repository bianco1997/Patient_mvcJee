package com.example.patient_mvc.securite.service;

import com.example.patient_mvc.securite.entities.AppRole;
import com.example.patient_mvc.securite.entities.AppUser;
import com.example.patient_mvc.securite.repositories.AppRoleRepository;
import com.example.patient_mvc.securite.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j //pour logé
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    // vous pouvez utilisez authowired ou allArgsConstructor
    /*public SecurityServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }*/

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword)) throw new RuntimeException("mdp ne corespent pas");
        String hashedPassword=passwordEncoder.encode(password);
        AppUser appUser=new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(hashedPassword);
        appUser.setActive(true);
        appUser.setUserId(UUID.randomUUID().toString());
        AppUser savedAppUser=appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole=appRoleRepository.findByRolename(roleName);
        if(appRole!=null) throw new RuntimeException("Role already exist");
        appRole=new AppRole();
        appRole.setRolename(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole=appRoleRepository.save(appRole);
        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRolename(roleName);
        if(appUser==null || appRole==null) throw new RuntimeException("user or role not found");
        appUser.getAppRoleList().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRolename(roleName);
        if(appUser==null || appRole==null) throw new RuntimeException("user or role not found");
        appUser.getAppRoleList().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
