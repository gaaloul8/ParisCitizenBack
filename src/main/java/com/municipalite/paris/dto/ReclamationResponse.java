package com.municipalite.paris.dto;

import com.municipalite.paris.entity.Reclamation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationResponse {
    private Long id;
    private String sujet;
    private String description;
    private LocalDateTime dateCreation;
    private LocalDateTime dateTraitement;
    private String statut;
    private String priorite;
    private String type;
    private String localisation;
    private String arrondissement;
    private String commentaires;
    private String pieceJointe;
    private Long citoyenId;
    private Long agentId;
    private Long municipaliteId;

    // Méthode pour convertir depuis l'entité Reclamation
    public static ReclamationResponse fromReclamation(Reclamation reclamation) {
        ReclamationResponse response = new ReclamationResponse();
        response.setId(reclamation.getId());
        response.setSujet(reclamation.getSujet());
        response.setDescription(reclamation.getDescription());
        response.setDateCreation(reclamation.getDateCreation());
        response.setDateTraitement(reclamation.getDateTraitement());
        response.setStatut(reclamation.getStatut() != null ? reclamation.getStatut().name() : null);
        response.setPriorite(reclamation.getPriorite() != null ? reclamation.getPriorite().name() : null);
        response.setType(reclamation.getType() != null ? reclamation.getType().name() : null);
        response.setLocalisation(reclamation.getLocalisation());
        response.setArrondissement(reclamation.getArrondissement());
        response.setCommentaires(reclamation.getCommentaires());
        response.setPieceJointe(reclamation.getPieceJointe());
        
        // Éviter les références circulaires en ne récupérant que les IDs
        if (reclamation.getCitoyen() != null) {
            response.setCitoyenId(reclamation.getCitoyen().getId());
        }
        if (reclamation.getAgent() != null) {
            response.setAgentId(reclamation.getAgent().getId());
        }
        if (reclamation.getMunicipalite() != null) {
            response.setMunicipaliteId(reclamation.getMunicipalite().getId());
        }
        
        return response;
    }
}






