package com.municipalite.paris.service.impl;

import com.municipalite.paris.dto.auth.AuthResponse;
import com.municipalite.paris.dto.auth.LoginRequest;
import com.municipalite.paris.dto.auth.RegisterRequest;
import com.municipalite.paris.entity.Admin;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.repository.AdminRepository;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.security.JwtTokenProvider;
import com.municipalite.paris.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final AgentMunicipalRepository agentRepository;
    private final CitoyenRepository citoyenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Essayer de se connecter en tant qu'admin
        try {
            return loginAdmin(request);
        } catch (RuntimeException e) {
            // Si ce n'est pas un admin, essayer agent
            try {
                return loginAgent(request);
            } catch (RuntimeException e2) {
                // Si ce n'est pas un agent, essayer citoyen
                try {
                    return loginCitoyen(request);
                } catch (RuntimeException e3) {
                    throw new RuntimeException("Email ou mot de passe incorrect");
                }
            }
        }
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (citoyenRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un compte avec cet email existe déjà");
        }

        Citoyen citoyen = new Citoyen();
        citoyen.setUsername(request.getEmail().split("@")[0]);
        citoyen.setEmail(request.getEmail());
        citoyen.setPassword(passwordEncoder.encode(request.getPassword()));
        citoyen.setNom(request.getNom());
        citoyen.setPrenom(request.getPrenom());
        citoyen.setCommune(request.getCommune());
        citoyen.setAge(request.getAge());
        citoyen.setTelephone(request.getTelephone());
        citoyen.setStatut(Citoyen.StatutCitoyen.ACTIF);

        citoyen = citoyenRepository.save(citoyen);

        String token = jwtTokenProvider.generateToken(
                citoyen.getEmail(),
                "citoyen",
                citoyen.getId(),
                null // Pas de municipalité assignée lors de l'inscription
        );

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .user(AuthResponse.UserInfo.builder()
                        .id(citoyen.getId())
                        .nom(citoyen.getNom())
                        .prenom(citoyen.getPrenom())
                        .email(citoyen.getEmail())
                        .role("citoyen")
                        .municipaliteId(citoyen.getMunicipalite() != null ? citoyen.getMunicipalite().getId() : null)
                        .build())
                .build();
    }

    private AuthResponse loginAdmin(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtTokenProvider.generateToken(
                admin.getEmail(),
                "admin",
                admin.getId(),
                null
        );

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .user(AuthResponse.UserInfo.builder()
                        .id(admin.getId())
                        .nom(admin.getNom())
                        .prenom(admin.getPrenom())
                        .email(admin.getEmail())
                        .role("admin")
                        .build())
                .build();
    }

    private AuthResponse loginAgent(LoginRequest request) {
        AgentMunicipal agent = agentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), agent.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtTokenProvider.generateToken(
                agent.getEmail(),
                "agent",
                agent.getId(),
                agent.getMunicipalite() != null ? agent.getMunicipalite().getId() : null
        );

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .user(AuthResponse.UserInfo.builder()
                        .id(agent.getId())
                        .nom(agent.getNom())
                        .prenom(agent.getPrenom())
                        .email(agent.getEmail())
                        .role("agent")
                        .municipaliteId(agent.getMunicipalite() != null ? agent.getMunicipalite().getId() : null)
                        .build())
                .build();
    }

    private AuthResponse loginCitoyen(LoginRequest request) {
        Citoyen citoyen = citoyenRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), citoyen.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtTokenProvider.generateToken(
                citoyen.getEmail(),
                "citoyen",
                citoyen.getId(),
                citoyen.getMunicipalite() != null ? citoyen.getMunicipalite().getId() : null
        );

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .user(AuthResponse.UserInfo.builder()
                        .id(citoyen.getId())
                        .nom(citoyen.getNom())
                        .prenom(citoyen.getPrenom())
                        .email(citoyen.getEmail())
                        .role("citoyen")
                        .municipaliteId(citoyen.getMunicipalite() != null ? citoyen.getMunicipalite().getId() : null)
                        .build())
                .build();
    }
}


