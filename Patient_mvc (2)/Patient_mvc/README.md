pour travailler avec spring securite 

sans spring security tout les requette arrive a DispatcherServlet

larsque on applique spring secirity tous les requtte arrive a Spring Security filtre

dans l'archetecture jee ya les filtre et les dispatcher

le filtre fonctionne par(requette besoin de s'autehentifier, si besoin d'un role specfique,si non)

spring a une configuration par default qui genere un utilisateur user avec un mdp generé par spring securite

la class de conf est:public class SecurityConfig extends WebSecurityConfigurerAdapter
    qui contient deux methede:
    NB les class de configuration continet l'annotation @Configuration
    pour spring security @EnableWebSecurity//pour activer la security weeb
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        comment spring security recherche les users et les roles 
        a travers BD, annuaire ou memory
    }
    protected void configure(HttpSecurity http) throws Exception {


xmlns:sec="http://www.thymeleaf.org/extras/spring-security" name space pour gere le context(les droit d'acces)

malgre d'utiliser ce name space ne suufit pas car je peux tout faire a partie de url grace a la fail ##security escalation##

solution:
http.authorizeRequests().antMatchers("/delete/**","edit/**","formPatient/**","/save/**").hasRole("ADMIN");
http.authorizeRequests().antMatchers("/index").hasRole("USER");