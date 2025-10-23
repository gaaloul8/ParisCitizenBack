package com.municipalite.paris.config;

import com.municipalite.paris.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/etablissements/**").permitAll() // Public access
                        .requestMatchers("/api/v1/projets/**").permitAll() // Public access
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/admins/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/municipalites/**").permitAll() // Public access for municipalites
                        .requestMatchers("/api/v1/agents/**").permitAll() // Public access for agents list
                        .requestMatchers("/api/v1/citoyens/{id}/projets-participes").permitAll() // Public access for participation check
                        .requestMatchers("/api/v1/citoyens/{id}/reclamations").permitAll() // Public access for citizen's reclamations
                        .requestMatchers("/api/v1/citoyens/**").permitAll() // Temporaire - permettre l'accès sans auth pour les tests
                        .requestMatchers("/api/v1/reclamations/**").permitAll() // Temporaire - permettre l'accès sans auth
                        .requestMatchers("/api/v1/reclamations-simple/**").permitAll() // Endpoint simplifié pour les réclamations
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


