
//PJESA E AUTORIZIMIT....
//FILLIMISHT E KAM BO QE ME I LEJU TE GJITHA ENDPOINTS PRA MOS ME BO KUFIZIME
//ne nje fare menyre eshte si mos me pas autorizim
package org.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // lejon qasje në çdo endpoint pa autorizim
                )
                .formLogin(AbstractHttpConfigurer::disable); // mos përdor form login HTML

        return http.build();
    }
}

