package com.example.patient_mvc.securite.repositories;

import com.example.patient_mvc.securite.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long>
{
    AppRole findByRolename(String roleName);
}
