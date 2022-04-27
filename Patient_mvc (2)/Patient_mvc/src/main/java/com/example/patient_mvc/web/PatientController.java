package com.example.patient_mvc.web;

import com.example.patient_mvc.entities.Patient;
import com.example.patient_mvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    //@Autowired ca vas marché mais pas recomendé il faut utilisee un constructeur parametré
    private PatientRepository patientRepository;
    //injection se fait via constructeur
    /*public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }*/
    @GetMapping(path = "/user/index")
    public  String patients(
            Model model,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size,
            @RequestParam(name = "KeyWord",defaultValue = "") String KeyWord) {
        Page<Patient> PagePatients=patientRepository.findByNomContains(KeyWord,PageRequest.of(page,size));
        //Page<Patient> PagePatients=patientRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listPatients",PagePatients.getContent());
        model.addAttribute("pages",new int[PagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("KeyWord",KeyWord);
        return "patients";//une vue basée sur lae moteur de recherche tymelift
    }
    @GetMapping ("/admin/delete")
    public String Delete(long id,String KeyWord,int page)
    {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&KeyWord="+KeyWord;
    }
    @GetMapping ("/")
    public String Home()
    {
        return "home";

    }
    //fonction qui retourne les patients en forma json
    @GetMapping("user/patients")
    @ResponseBody
    public List<Patient> Listpatients()
    {
        return patientRepository.findAll();
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        //pour des valeur par default
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping("/admin/save")
    public String save(
            Model model,
            @Valid Patient patient,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page)
    {
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    /*
    *donc pour faire la validation des formulaire vous avez besoin de 3 chose a faire
    * ajouter la dependences spring boot validation
    * ajouter les annotations de validation au niveu des entites
    * ajouter @Valid et BindingResult au niveau du controller
    * ajouter th:errors au niveau du page html
     */
    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,Long id,String keyword,int page){
        //pour des valeur par default
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editPatient";
    }

    /*
    * pour le rendu cote serveur
    * pour pouvoir garder l"etat de lapplication il faut envoyer les param d'une page a l'autre
     */



}
