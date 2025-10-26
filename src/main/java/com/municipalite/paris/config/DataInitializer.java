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
            log.info("📋 Initialisation des 20 arrondissements de Paris...");
            
            // 1er arrondissement
            createMunicipalite("Mairie du 1er arrondissement", "Île-de-France", "75001", 
                new BigDecimal("15000000.00"), "4 Place du Louvre, 75001 Paris", 
                "01 44 50 75 01", "mairie01@paris.fr", "https://mairie01.paris.fr");
            
            // 2ème arrondissement
            createMunicipalite("Mairie du 2ème arrondissement", "Île-de-France", "75002", 
                new BigDecimal("12000000.00"), "8 Rue de la Banque, 75002 Paris", 
                "01 44 50 75 02", "mairie02@paris.fr", "https://mairie02.paris.fr");
            
            // 3ème arrondissement
            createMunicipalite("Mairie du 3ème arrondissement", "Île-de-France", "75003", 
                new BigDecimal("18000000.00"), "2 Rue Eugène Spuller, 75003 Paris", 
                "01 44 50 75 03", "mairie03@paris.fr", "https://mairie03.paris.fr");
            
            // 4ème arrondissement
            createMunicipalite("Mairie du 4ème arrondissement", "Île-de-France", "75004", 
                new BigDecimal("22000000.00"), "2 Place Baudoyer, 75004 Paris", 
                "01 44 50 75 04", "mairie04@paris.fr", "https://mairie04.paris.fr");
            
            // 5ème arrondissement
            createMunicipalite("Mairie du 5ème arrondissement", "Île-de-France", "75005", 
                new BigDecimal("25000000.00"), "21 Place du Panthéon, 75005 Paris", 
                "01 44 50 75 05", "mairie05@paris.fr", "https://mairie05.paris.fr");
            
            // 6ème arrondissement
            createMunicipalite("Mairie du 6ème arrondissement", "Île-de-France", "75006", 
                new BigDecimal("20000000.00"), "78 Rue Bonaparte, 75006 Paris", 
                "01 44 50 75 06", "mairie06@paris.fr", "https://mairie06.paris.fr");
            
            // 7ème arrondissement
            createMunicipalite("Mairie du 7ème arrondissement", "Île-de-France", "75007", 
                new BigDecimal("30000000.00"), "116 Rue de Grenelle, 75007 Paris", 
                "01 44 50 75 07", "mairie07@paris.fr", "https://mairie07.paris.fr");
            
            // 8ème arrondissement
            createMunicipalite("Mairie du 8ème arrondissement", "Île-de-France", "75008", 
                new BigDecimal("35000000.00"), "3 Rue de Lisbonne, 75008 Paris", 
                "01 44 50 75 08", "mairie08@paris.fr", "https://mairie08.paris.fr");
            
            // 9ème arrondissement
            createMunicipalite("Mairie du 9ème arrondissement", "Île-de-France", "75009", 
                new BigDecimal("28000000.00"), "6 Rue Drouot, 75009 Paris", 
                "01 44 50 75 09", "mairie09@paris.fr", "https://mairie09.paris.fr");
            
            // 10ème arrondissement
            createMunicipalite("Mairie du 10ème arrondissement", "Île-de-France", "75010", 
                new BigDecimal("32000000.00"), "72 Rue du Faubourg-Saint-Martin, 75010 Paris", 
                "01 44 50 75 10", "mairie10@paris.fr", "https://mairie10.paris.fr");
            
            // 11ème arrondissement
            createMunicipalite("Mairie du 11ème arrondissement", "Île-de-France", "75011", 
                new BigDecimal("40000000.00"), "12 Place Léon-Blum, 75011 Paris", 
                "01 44 50 75 11", "mairie11@paris.fr", "https://mairie11.paris.fr");
            
            // 12ème arrondissement
            createMunicipalite("Mairie du 12ème arrondissement", "Île-de-France", "75012", 
                new BigDecimal("45000000.00"), "130 Avenue Daumesnil, 75012 Paris", 
                "01 44 50 75 12", "mairie12@paris.fr", "https://mairie12.paris.fr");
            
            // 13ème arrondissement
            createMunicipalite("Mairie du 13ème arrondissement", "Île-de-France", "75013", 
                new BigDecimal("50000000.00"), "1 Place d'Italie, 75013 Paris", 
                "01 44 50 75 13", "mairie13@paris.fr", "https://mairie13.paris.fr");
            
            // 14ème arrondissement
            createMunicipalite("Mairie du 14ème arrondissement", "Île-de-France", "75014", 
                new BigDecimal("38000000.00"), "2 Place Ferdinand-Brunot, 75014 Paris", 
                "01 44 50 75 14", "mairie14@paris.fr", "https://mairie14.paris.fr");
            
            // 15ème arrondissement
            createMunicipalite("Mairie du 15ème arrondissement", "Île-de-France", "75015", 
                new BigDecimal("55000000.00"), "31 Rue Péclet, 75015 Paris", 
                "01 44 50 75 15", "mairie15@paris.fr", "https://mairie15.paris.fr");
            
            // 16ème arrondissement
            createMunicipalite("Mairie du 16ème arrondissement", "Île-de-France", "75016", 
                new BigDecimal("60000000.00"), "71 Avenue Henri-Martin, 75016 Paris", 
                "01 44 50 75 16", "mairie16@paris.fr", "https://mairie16.paris.fr");
            
            // 17ème arrondissement
            createMunicipalite("Mairie du 17ème arrondissement", "Île-de-France", "75017", 
                new BigDecimal("42000000.00"), "16 Rue des Batignolles, 75017 Paris", 
                "01 44 50 75 17", "mairie17@paris.fr", "https://mairie17.paris.fr");
            
            // 18ème arrondissement
            createMunicipalite("Mairie du 18ème arrondissement", "Île-de-France", "75018", 
                new BigDecimal("48000000.00"), "1 Place Jules-Joffrin, 75018 Paris", 
                "01 44 50 75 18", "mairie18@paris.fr", "https://mairie18.paris.fr");
            
            // 19ème arrondissement
            createMunicipalite("Mairie du 19ème arrondissement", "Île-de-France", "75019", 
                new BigDecimal("52000000.00"), "5 Place Armand-Carrel, 75019 Paris", 
                "01 44 50 75 19", "mairie19@paris.fr", "https://mairie19.paris.fr");
            
            // 20ème arrondissement
            createMunicipalite("Mairie du 20ème arrondissement", "Île-de-France", "75020", 
                new BigDecimal("46000000.00"), "6 Place Gambetta, 75020 Paris", 
                "01 44 50 75 20", "mairie20@paris.fr", "https://mairie20.paris.fr");
            
            // Ajouter automatiquement les municipalités supplémentaires
            addAdditionalMunicipalites();
            
            log.info("✅ 20 arrondissements de Paris + municipalités supplémentaires initialisés avec succès !");
        } else {
            log.info("ℹ️ Les municipalités existent déjà dans la base de données.");
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
    
    private void createMunicipalite(String nom, String region, String codePostal, 
                                  BigDecimal budgetAnnuel, String adresse, 
                                  String telephone, String email, String siteWeb) {
        Municipalite municipalite = new Municipalite();
        municipalite.setNom(nom);
        municipalite.setRegion(region);
        municipalite.setCodePostal(codePostal);
        municipalite.setBudgetAnnuel(budgetAnnuel);
        municipalite.setAdresse(adresse);
        municipalite.setTelephone(telephone);
        municipalite.setEmail(email);
        municipalite.setSiteWeb(siteWeb);
        municipalite.setDateCreation(LocalDate.now());
        municipalite.setStatut(Municipalite.StatutMunicipalite.ACTIVE);
        municipalite.setTauxSatisfaction(new BigDecimal("85")); // Taux par défaut
        
        municipaliteRepository.save(municipalite);
        log.info("✅ " + nom + " créé");
    }
    
    private void addAdditionalMunicipalites() {
        log.info("🏙️ Ajout automatique des municipalités supplémentaires...");
        
        // Liste de toutes les municipalités supplémentaires à ajouter
        String[][] municipalitesData = {
            // Nom, Code Postal, Budget, Adresse, Téléphone, Email, Site Web
            {"Mairie de Neuilly-sur-Seine", "92200", "25000000.00", "96 Avenue du Général de Gaulle, 92200 Neuilly-sur-Seine", "01 40 88 88 88", "contact@neuillysurseine.fr", "https://www.neuillysurseine.fr"},
            {"Mairie de Boulogne-Billancourt", "92100", "30000000.00", "26 Avenue André-Morizet, 92100 Boulogne-Billancourt", "01 41 41 41 41", "contact@boulognebillancourt.com", "https://www.boulognebillancourt.com"},
            {"Mairie de Levallois-Perret", "92300", "28000000.00", "1 Place de la République, 92300 Levallois-Perret", "01 47 15 71 00", "contact@levallois.fr", "https://www.levallois.fr"},
            {"Mairie d'Issy-les-Moulineaux", "92130", "22000000.00", "40 Rue de la République, 92130 Issy-les-Moulineaux", "01 41 23 80 00", "contact@issy.com", "https://www.issy.com"},
            {"Mairie de Clichy", "92110", "18000000.00", "Place du 11 Novembre 1918, 92110 Clichy", "01 47 15 80 00", "contact@ville-clichy.fr", "https://www.ville-clichy.fr"},
            {"Mairie d'Asnières-sur-Seine", "92600", "20000000.00", "1 Place de l'Hôtel de Ville, 92600 Asnières-sur-Seine", "01 41 11 12 13", "contact@asnieres-sur-seine.fr", "https://www.asnieres-sur-seine.fr"},
            {"Mairie de Versailles", "78000", "45000000.00", "4 Avenue de Paris, 78000 Versailles", "01 30 97 80 00", "contact@versailles.fr", "https://www.versailles.fr"},
            {"Mairie de Nanterre", "92000", "35000000.00", "1 Place Nelson Mandela, 92000 Nanterre", "01 47 24 60 00", "contact@nanterre.fr", "https://www.nanterre.fr"},
            {"Mairie de Créteil", "94000", "40000000.00", "Place Salvador Allende, 94000 Créteil", "01 58 43 43 43", "contact@ville-creteil.fr", "https://www.ville-creteil.fr"},
            {"Mairie de Bobigny", "93000", "32000000.00", "1 Avenue de la République, 93000 Bobigny", "01 48 30 60 00", "contact@bobigny.fr", "https://www.bobigny.fr"},
            {"Mairie de Saint-Denis", "93200", "38000000.00", "2 Place du Caquet, 93200 Saint-Denis", "01 49 33 60 00", "contact@ville-saint-denis.fr", "https://www.ville-saint-denis.fr"},
            {"Mairie de Montreuil", "93100", "28000000.00", "1 Place Jean Jaurès, 93100 Montreuil", "01 48 70 60 00", "contact@montreuil.fr", "https://www.montreuil.fr"},
            {"Mairie de Pantin", "93500", "25000000.00", "84 Avenue du Général Leclerc, 93500 Pantin", "01 49 15 40 00", "contact@ville-pantin.fr", "https://www.ville-pantin.fr"},
            {"Mairie d'Aubervilliers", "93300", "22000000.00", "31-33 Rue de la Commune de Paris, 93300 Aubervilliers", "01 48 39 50 00", "contact@aubervilliers.fr", "https://www.aubervilliers.fr"},
            {"Mairie de Noisy-le-Grand", "93160", "20000000.00", "1 Place de la République, 93160 Noisy-le-Grand", "01 45 92 40 00", "contact@noisylegrand.fr", "https://www.noisylegrand.fr"}
        };
        
        int ajoutees = 0;
        
        // Parcourir toutes les municipalités et les ajouter automatiquement
        for (String[] data : municipalitesData) {
            createMunicipalite(
                data[0], // Nom
                "Île-de-France",
                data[1], // Code postal
                new BigDecimal(data[2]), // Budget
                data[3], // Adresse
                data[4], // Téléphone
                data[5], // Email
                data[6]  // Site web
            );
            ajoutees++;
        }
        
        log.info("✅ {} municipalités supplémentaires ajoutées automatiquement", ajoutees);
    }
}


