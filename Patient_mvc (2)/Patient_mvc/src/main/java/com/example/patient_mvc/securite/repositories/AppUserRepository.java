package com.example.patient_mvc.securite.repositories;

import com.example.patient_mvc.securite.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String>
{
    AppUser findByUsername(String username);
}
