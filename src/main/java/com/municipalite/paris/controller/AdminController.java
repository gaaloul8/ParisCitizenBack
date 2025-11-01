package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.repository.ProjetRepository;
import com.municipalite.paris.repository.ReclamationRepository;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.MunicipaliteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CitoyenRepository citoyenRepository;
    private final ProjetRepository projetRepository;
    private final ReclamationRepository reclamationRepository;
    private final AgentMunicipalRepository agentRepository;
    private final MunicipaliteRepository municipaliteRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getAdminStats() {
        try {
            // Récupérer les données de base
            long totalCitoyens = citoyenRepository.count();
            long totalProjets = projetRepository.count();
            long totalReclamations = reclamationRepository.count();
            long totalAgents = agentRepository.count();
            long totalMunicipalites = municipaliteRepository.count();

            // Calculer l'âge moyen des citoyens
            List<Citoyen> citoyens = citoyenRepository.findAll();
            double moyenneAge = citoyens.stream()
                    .filter(c -> c.getAge() != null)
                    .mapToInt(Citoyen::getAge)
                    .average()
                    .orElse(0.0);

            // Calculer le taux de satisfaction global (simulation)
            double tauxSatisfactionGlobal = 85.5;

            // Générer l'activité mensuelle (simulation)
            List<Map<String, Object>> activiteMensuelle = generateActiviteMensuelle();

            // Top municipalités (simulation)
            List<Map<String, Object>> topMunicipalites = generateTopMunicipalites();

            // Répartition par âge (simulation)
            List<Map<String, Object>> repartitionParAge = generateRepartitionParAge();

            // Créer la réponse
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalCitoyens", totalCitoyens);
            stats.put("moyenneAge", Math.round(moyenneAge * 100.0) / 100.0);
            stats.put("totalMunicipalites", totalMunicipalites);
            stats.put("totalAgents", totalAgents);
            stats.put("totalProjets", totalProjets);
            stats.put("totalReclamations", totalReclamations);
            stats.put("tauxSatisfactionGlobal", tauxSatisfactionGlobal);
            stats.put("activiteMensuelle", activiteMensuelle);
            stats.put("topMunicipalites", topMunicipalites);
            stats.put("repartitionParAge", repartitionParAge);

            return ResponseEntity.ok(ApiResponse.success(stats));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Erreur lors du calcul des statistiques: " + e.getMessage()));
        }
    }

    private List<Map<String, Object>> generateActiviteMensuelle() {
        List<Map<String, Object>> activite = new ArrayList<>();
        String[] mois = {"Jan", "Fév", "Mar", "Avr", "Mai", "Juin"};
        
        for (int i = 0; i < mois.length; i++) {
            Map<String, Object> moisData = new HashMap<>();
            moisData.put("mois", mois[i]);
            moisData.put("nouveauxCitoyens", 15 + (i * 3));
            moisData.put("nouvellesReclamations", 25 + (i * 5));
            moisData.put("projetsLances", 3 + i);
            activite.add(moisData);
        }
        return activite;
    }

    private List<Map<String, Object>> generateTopMunicipalites() {
        List<Map<String, Object>> topMunicipalites = new ArrayList<>();
        
        Map<String, Object> muni1 = new HashMap<>();
        muni1.put("nom", "15ème Arrondissement");
        muni1.put("tauxSatisfaction", 92.5);
        muni1.put("nombreReclamations", 45);
        topMunicipalites.add(muni1);

        Map<String, Object> muni2 = new HashMap<>();
        muni2.put("nom", "1er Arrondissement");
        muni2.put("tauxSatisfaction", 88.3);
        muni2.put("nombreReclamations", 38);
        topMunicipalites.add(muni2);

        Map<String, Object> muni3 = new HashMap<>();
        muni3.put("nom", "20ème Arrondissement");
        muni3.put("tauxSatisfaction", 85.7);
        muni3.put("nombreReclamations", 52);
        topMunicipalites.add(muni3);

        return topMunicipalites;
    }

    private List<Map<String, Object>> generateRepartitionParAge() {
        List<Map<String, Object>> repartition = new ArrayList<>();
        
        String[] tranches = {"18-25", "26-35", "36-45", "46-55", "56-65", "65+"};
        int[] nombres = {120, 180, 150, 130, 95, 80};
        int total = Arrays.stream(nombres).sum();
        
        for (int i = 0; i < tranches.length; i++) {
            Map<String, Object> tranche = new HashMap<>();
            tranche.put("tranche", tranches[i]);
            tranche.put("nombre", nombres[i]);
            tranche.put("pourcentage", Math.round((double) nombres[i] / total * 100 * 100.0) / 100.0);
            repartition.add(tranche);
        }
        
        return repartition;
    }
}
