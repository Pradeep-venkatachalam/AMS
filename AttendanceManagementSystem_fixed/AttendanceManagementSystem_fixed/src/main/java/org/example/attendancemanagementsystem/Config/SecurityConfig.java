package org.example.attendancemanagementsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Allow all static resources and auth endpoints without login
                        .requestMatchers(
                                "/", "/index.html", "/login.html",
                                "/*.css", "/*.js", "/*.html",
                                "/auth/login", "/auth/register"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
