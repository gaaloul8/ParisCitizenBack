package com.municipalite.paris.config;

import com.municipalite.paris.entity.Admin;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.repository.AdminRepository;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.repository.MunicipaliteRepository;
import com.municipalite.paris.repository.ProjetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final MunicipaliteRepository municipaliteRepository;
    private final AgentMunicipalRepository agentRepository;
    private final CitoyenRepository citoyenRepository;
    private final ProjetRepository projetRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("=== Initialisation des données par défaut ===");
        
        initializeAdmins();
        initializeMunicipalites();
        initializeAgents();
        initializeCitoyens();
        initializeProjets();
        
        log.info("=== Initialisation terminée ===");
    }

    private void initializeAdmins() {
        // Vérifier si l'admin par défaut existe déjà
        Admin existingAdmin = adminRepository.findByEmail("admin@paris.fr").orElse(null);
        
        if (existingAdmin == null) {
            // Créer un nouvel admin
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setEmail("admin@paris.fr");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setNom("Admin");
            admin.setPrenom("Super");
            admin.setRole(Admin.RoleAdmin.SUPER_ADMIN);
            admin.setStatut(Admin.StatutAdmin.ACTIF);
            adminRepository.save(admin);
            log.info("✓ Admin par défaut créé (email: admin@paris.fr, password: admin)");
        } else {
            // Mettre à jour l'admin existant pour s'assurer qu'il est actif
            existingAdmin.setStatut(Admin.StatutAdmin.ACTIF);
            existingAdmin.setRole(Admin.RoleAdmin.SUPER_ADMIN);
            existingAdmin.setPassword(passwordEncoder.encode("admin")); // Reset password
            adminRepository.save(existingAdmin);
            log.info("✓ Admin par défaut mis à jour (email: admin@paris.fr, password: admin)");
        }
    }

    private void initializeMunicipalites() {
        if (municipaliteRepository.count() == 0) {
            Municipalite mun1 = new Municipalite();
            mun1.setNom("Mairie du 15ème arrondissement");
            mun1.setRegion("Île-de-France");
            mun1.setCodePostal("75015");
            mun1.setBudgetAnnuel(new BigDecimal("12000000"));
            mun1.setTauxSatisfaction(new BigDecimal("92"));
            mun1.setDateCreation(LocalDate.of(2019, 9, 1));
            mun1.setStatut(Municipalite.StatutMunicipalite.ACTIVE);
            mun1.setAdresse("31 Rue Péclet, 75015 Paris");
            mun1.setTelephone("01 55 76 75 15");
            mun1.setEmail("contact@mairie15.paris.fr");
            municipaliteRepository.save(mun1);

            Municipalite mun2 = new Municipalite();
            mun2.setNom("Mairie du 1er arrondissement");
            mun2.setRegion("Île-de-France");
            mun2.setCodePostal("75001");
            mun2.setBudgetAnnuel(new BigDecimal("4500000"));
            mun2.setTauxSatisfaction(new BigDecimal("87"));
            mun2.setDateCreation(LocalDate.of(2020, 1, 15));
            mun2.setStatut(Municipalite.StatutMunicipalite.ACTIVE);
            municipaliteRepository.save(mun2);

            log.info("✓ Municipalités par défaut créées");
        }
    }

    private void initializeAgents() {
        if (agentRepository.count() == 0) {
            Municipalite mun = municipaliteRepository.findById(1L).orElse(null);
            if (mun != null) {
                AgentMunicipal agent = new AgentMunicipal();
                agent.setUsername("agent");
                agent.setEmail("agent@paris.fr");
                agent.setPassword(passwordEncoder.encode("agent"));
                agent.setNom("Martin");
                agent.setPrenom("Jean");
                agent.setMunicipalite(mun);
                agent.setPoste("Responsable réclamations");
                agent.setDateEmbauche(LocalDate.of(2019, 3, 15));
                agent.setStatut(AgentMunicipal.StatutAgent.ACTIF);
                agent.setNoteSatisfaction(new BigDecimal("4.2"));
                agentRepository.save(agent);
                log.info("✓ Agent par défaut créé (email: agent@paris.fr, password: agent)");
            }
        }
    }

    private void initializeCitoyens() {
        if (citoyenRepository.count() == 0) {
            Municipalite mun = municipaliteRepository.findById(1L).orElse(null);
            if (mun != null) {
                Citoyen citoyen = new Citoyen();
                citoyen.setUsername("citoyen");
                citoyen.setEmail("citoyen@example.com");
                citoyen.setPassword(passwordEncoder.encode("citoyen"));
                citoyen.setNom("Dupont");
                citoyen.setPrenom("Marie");
                citoyen.setAge(34);
                citoyen.setCommune("15ème arrondissement");
                citoyen.setMunicipalite(mun);
                citoyen.setStatut(Citoyen.StatutCitoyen.ACTIF);
                citoyenRepository.save(citoyen);
                log.info("✓ Citoyen par défaut créé (email: citoyen@example.com, password: citoyen)");
            }
        }
    }

    private void initializeProjets() {
        if (projetRepository.count() == 0) {
            Municipalite mun = municipaliteRepository.findById(1L).orElse(null);
            AgentMunicipal agent = agentRepository.findByEmail("agent@paris.fr").orElse(null);
            
            if (mun != null && agent != null) {
                // Projet 1
                Projet projet1 = new Projet();
                projet1.setTitre("Rénovation des espaces verts");
                projet1.setDescription("Amélioration des parcs et jardins publics du 15ème arrondissement");
                projet1.setDateDebut(LocalDate.of(2024, 3, 1));
                projet1.setDateFin(LocalDate.of(2024, 12, 31));
                projet1.setStatut(Projet.StatutProjet.ACTIF);
                projet1.setBudget(new BigDecimal("45000.00"));
                projet1.setResponsable(agent);
                projet1.setMunicipalite(mun);
                projet1.setArrondissement("15ème");
                projet1.setNombreParticipants(12);
                projet1.setLocalisation("15ème arrondissement");
                projetRepository.save(projet1);

                // Projet 2
                Projet projet2 = new Projet();
                projet2.setTitre("Sécurisation des passages piétons");
                projet2.setDescription("Installation de feux tricolores et marquage au sol");
                projet2.setDateDebut(LocalDate.of(2024, 4, 15));
                projet2.setDateFin(LocalDate.of(2024, 8, 30));
                projet2.setStatut(Projet.StatutProjet.ACTIF);
                projet2.setBudget(new BigDecimal("28000.00"));
                projet2.setResponsable(agent);
                projet2.setMunicipalite(mun);
                projet2.setArrondissement("15ème");
                projet2.setNombreParticipants(8);
                projet2.setLocalisation("15ème arrondissement");
                projetRepository.save(projet2);

                // Projet 3
                Projet projet3 = new Projet();
                projet3.setTitre("Événement culturel d'été");
                projet3.setDescription("Organisation d'un festival de musique dans le parc");
                projet3.setDateDebut(LocalDate.of(2024, 6, 15));
                projet3.setDateFin(LocalDate.of(2024, 7, 15));
                projet3.setStatut(Projet.StatutProjet.BROUILLON);
                projet3.setBudget(new BigDecimal("15000.00"));
                projet3.setResponsable(agent);
                projet3.setMunicipalite(mun);
                projet3.setArrondissement("15ème");
                projet3.setNombreParticipants(25);
                projet3.setLocalisation("15ème arrondissement");
                projetRepository.save(projet3);

                log.info("✓ Projets par défaut créés (3 projets)");
            }
        }
    }
}


