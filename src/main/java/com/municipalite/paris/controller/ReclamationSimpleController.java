package com.municipalite.paris.controller;

import com.municipalite.paris.dto.CreateReclamationRequest;
import com.municipalite.paris.dto.ReclamationResponse;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.repository.ReclamationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reclamations-simple")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationSimpleController {

    private final ReclamationRepository reclamationRepository;
    private final CitoyenRepository citoyenRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> createReclamation(@RequestBody CreateReclamationRequest request) {
        try {
            System.out.println("=== DEBUG RECLAMATION ===");
            System.out.println("Request reçu: " + request);
            System.out.println("Citoyen ID: " + request.getCitoyenId());
            
            // Vérifier que le citoyen existe
            boolean citoyenExists = citoyenRepository.existsById(request.getCitoyenId());
            System.out.println("Citoyen existe: " + citoyenExists);
            
            if (!citoyenExists) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", true);
                errorResponse.put("message", "Citoyen non trouvé avec l'ID: " + request.getCitoyenId());
                errorResponse.put("code", "CITOYEN_NOT_FOUND");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Créer la réclamation
            Reclamation reclamation = new Reclamation();
            reclamation.setSujet(request.getSujet());
            reclamation.setDescription(request.getDescription());
            reclamation.setLocalisation(request.getLocalisation());
            reclamation.setArrondissement(request.getArrondissement());
            reclamation.setCommentaires(request.getCommentaires());
            reclamation.setPieceJointe(request.getPieceJointe());
            
            // Conversion des enums
            if (request.getType() != null) {
                reclamation.setType(Reclamation.TypeReclamation.valueOf(request.getType()));
            }
            if (request.getPriorite() != null) {
                reclamation.setPriorite(Reclamation.PrioriteReclamation.valueOf(request.getPriorite()));
            }
            if (request.getStatut() != null) {
                reclamation.setStatut(Reclamation.StatutReclamation.valueOf(request.getStatut()));
            }

            // Créer un citoyen minimal pour éviter les proxies
            Citoyen citoyen = new Citoyen();
            citoyen.setId(request.getCitoyenId());
            reclamation.setCitoyen(citoyen);

            // Sauvegarder
            System.out.println("Sauvegarde de la réclamation...");
            Reclamation savedReclamation = reclamationRepository.save(reclamation);
            System.out.println("Réclamation sauvegardée avec ID: " + savedReclamation.getId());

            // Créer la réponse sans références circulaires
            Map<String, Object> response = new HashMap<>();
            response.put("error", false);
            response.put("message", "Réclamation créée avec succès");
            response.put("data", Map.of(
                "id", savedReclamation.getId(),
                "sujet", savedReclamation.getSujet(),
                "description", savedReclamation.getDescription(),
                "localisation", savedReclamation.getLocalisation(),
                "arrondissement", savedReclamation.getArrondissement(),
                "statut", savedReclamation.getStatut() != null ? savedReclamation.getStatut().name() : null,
                "priorite", savedReclamation.getPriorite() != null ? savedReclamation.getPriorite().name() : null,
                "type", savedReclamation.getType() != null ? savedReclamation.getType().name() : null,
                "citoyenId", request.getCitoyenId()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "Erreur lors de la création de la réclamation: " + e.getMessage());
            errorResponse.put("code", "CREATION_ERROR");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}


