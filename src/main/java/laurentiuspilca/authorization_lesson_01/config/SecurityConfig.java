package laurentiuspilca.authorization_lesson_01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic()
                .and()
                .authorizeHttpRequests()
                //.authorizeRequests() // DEPRECATED WAY BUT LAURENTİU SPİLCA'S WAY
                // .requestMatchers("/demo/**") //DEMO UZANTILI END POİNT'E GELEN BÜTÜN REQUESTLERİ KAPSAR
                //.anyRequest()//MATCHER METHOD (BÜTÜN GELEN İSTEKLER)
                //.anyRequest().hasAuthority("ADMIN") //AUTHORİZATİON RULE (SADECE ADMIN OLANLAR)
                //.anyRequest().permitAll()///HEPSİNE İZİN VERİR
                //.anyRequest().denyAll() //HEPSİNİ REDDEDER
                //.anyRequest().hasRole("ADMIN")// (hasAuthority() ile eşdeğerdir)
                //.anyRequest().hasAnyRole("ADMIN","USER")
                //.anyRequest().access(new WebExpressionAuthorizationManager("isAuthenticated() and hasAuthority('ADMIN') "))//DAHA KARIŞIK LOCİGLER YAZMAK İÇİN
                //.requestMatchers(HttpMethod.GET,"/demo/**").hasAuthority("ADMIN").anyRequest().authenticated() //DEMO END POİNT'DEN ALINAN GET METODLARINDA ADMIN'LİK İSTER, GERİ KALAN HERŞEYDE AUTHANTİCATED OLMAK YETERLİ
                .anyRequest().authenticated()
                //.and().csrf().disable().build(); // NEVER DON'T DO THİS IN REAL WORLD PROJECTS(POSTMANDAN POST ATABİLMEK İÇİN GEREKLİ)
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        // BELLEKTE BİR KULLANICI OLUŞTURDUM
        var uds = new InMemoryUserDetailsManager();
        var admin = User.withUsername("admin").password(passwordEncoder().encode("123")).authorities("ADMIN").build();
        var user = User.withUsername("user").password(passwordEncoder().encode("123")).authorities("USER").build();
        uds.createUser(admin);
        uds.createUser(user);
        return uds;


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
