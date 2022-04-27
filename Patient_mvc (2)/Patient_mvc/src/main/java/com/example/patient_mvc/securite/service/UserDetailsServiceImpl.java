package com.example.patient_mvc.securite.service;

import com.example.patient_mvc.securite.entities.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private SecurityService securityService;

    public UserDetailsServiceImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=securityService.loadUserByUsername(username);
        /*Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getAppRoleList().forEach(role->{
            SimpleGrantedAuthority authority= new SimpleGrantedAuthority(role.getRolename());
            authorities.add(authority);
        });*/

        /*a travers la class securityConfig dans la method configure quan l'utilisateur vas saisir username + psword
        *tu fais appele a l'objet userdetail service qui contient la methode load userbyusername
        */
        //une facon tres pratique (declarative) au lieu de l'imperatif classic en haut
        Collection<GrantedAuthority> authorities1=
                appUser.getAppRoleList()
                        .stream()
                        .map(role->new SimpleGrantedAuthority(role.getRolename()))
                        .collect(Collectors.toList());
        User user = new User(appUser.getUsername(),appUser.getPassword(),authorities1);
        return user;
    }
}
