package com.example.patient_mvc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor//lambok qui socupe de la generation des const et getter setter

@Entity // cette represente une table dans la base de donnée

public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//primary key auto increment
    private Long id;
    @NotEmpty
    @Size(min = 4,max = 40)
    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@Column(name ="date de naissance",length = 10)
    private Date dateNaiss;
    private boolean malade;
    //@DecimalMin("100")
    private int score;

    public Patient(String s, String s1, Date date, boolean b, int i) {
    }
}
