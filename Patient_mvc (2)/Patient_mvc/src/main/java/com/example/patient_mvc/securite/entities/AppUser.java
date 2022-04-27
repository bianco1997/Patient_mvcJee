package com.example.patient_mvc.securite.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean active;
    //un user peut avoir plusieurs roles et un role concercne plusieur utilisateur
    @ManyToMany(fetch = FetchType.EAGER)//a chaque fois quand je charge un users hybernet charge tous ses roles
    private List<AppRole> appRoleList=new ArrayList<>();
}
