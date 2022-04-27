package com.example.patient_mvc.securite;

import com.example.patient_mvc.securite.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration//c'est une class de configuration
/*
* tous les class qui contiet les annotations @Configuration s'execute(s'instencier) en premier lieu(au demmarage)
*/
@EnableWebSecurity//pour activer la security weeb
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired //injecter le meme data source declarer dans aplicationprperties
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    /*@Autowired
    private  PasswordEncoder passwordEncoder;*/
    //on vas redefiner cest de methode afin de re configurer spring security(users,role....)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //preciser qelle sont les users et les roles et ou ils sont
        // (BD,local,Memory,annuaired'entreprise like lDap)
        /*PasswordEncoder passwordEncoder=passwordEncoder();
        String encodedPWD=passwordEncoder.encode("1234");*/
        /*
        * c'est deux ligne est pour hasher le mdp par Bcrypt
        */
        /*System.out.println(encodedPWD);
        auth.inMemoryAuthentication().
                withUser("user1").
                password((encodedPWD)).//{noop} n'utiliser pas une fonction de hashage
                roles("USER").and().
                withUser("admin").
                password(encodedPWD).
                roles("USER","ADMIN");
        /*auth.inMemoryAuthentication().
                withUser("user1").
                password(("{noop}"1234")).//{noop} n'utiliser pas une fonction de hashage
                roles("USER").and().
                withUser("admin").
                password("1234").
                roles("USER","ADMIN");*/
        //vous pouvez utiliser auth.inMemoryAuthentication() au lieu de and

        // version base de donnée
        /*PasswordEncoder passwordEncoder=passwordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource).
                usersByUsernameQuery("select username as principal,password credentials,active from users where username=?").
                authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?").
                rolePrefix("ROLE_").
                passwordEncoder(passwordEncoder);*/
        //user details
        /*
        * pour utiliser users details servce il defaut cree un objet qui implement l'interface UserDetailsService() qui definie une methode loadUserByUsername
        * donc c'est a vous de savoir comment vous allez authentifiée vos utilsateur(la couche metier) au lieu de spring*/
        auth.userDetailsService(userDetailsService);
        //au moment de l'auryhentifaction spring ila vas faire appele a userdetails service avec loaduserbyusername


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //cest pour cpecifier les droit d'acces
        http.formLogin();//pour utiliser un formulaire de login
        http.authorizeRequests().antMatchers("/").permitAll();
        //http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        //http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
        //si vous utiliser un formulaire bien specifier vous pouvez utiliser http.formLogin().loginPage("/login"(action dans un controller);
        //http.authorizeRequests().antMatchers("/delete/**","/edit/**","/formPatients/**","/save/**").hasRole("ADMIN");
        //donc pour organisez votre travailler il faut separer les page d'admin et le page de users
        //http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        //http.authorizeRequests().antMatchers("/index").hasRole("USER");
        //http.authorizeRequests().antMatchers("/user/**").hasRole("USER");

        //toute les url qui contient /delete/quelque chose besoin d'un role admin
        //http.authorizeRequests().anyRequest().authenticated();//tous les requettes http nessecite une athentification
        //fait attention les mdp sont stocker d'une manierre hashe

        //http.exceptionHandling().accessDeniedPage("/403");
        //je vais cree une 403 qui retourne une page 403



    }

}
