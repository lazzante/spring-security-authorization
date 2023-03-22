package laurentiuspilca.authorization_lesson_01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
                .authorizeRequests()
                // .requestMatchers("/demo/**") DEMO UZANTILI END POİNT'E GELEN BÜTÜN REQUESTLERİ KAPSAR
                .anyRequest()//MATCHER METHOD (BÜTÜN GELEN İSTEKLER)
                .hasAuthority("ADMIN") //AUTHORİZATİON RULE (SADECE ADMIN OLANLAR)
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        // BELLEKTE BİR KULLANICI OLUŞTURDUM
        var uds = new InMemoryUserDetailsManager();
        var a1 = User.withUsername("admin").password(passwordEncoder().encode("123")).authorities("ADMIN").build();
        var u2 = User.withUsername("user").password(passwordEncoder().encode("123")).authorities("USER").build();
        uds.createUser(a1);
        uds.createUser(u2);
        return uds;


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
