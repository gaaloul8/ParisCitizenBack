package com.municipalite.paris.dto;

import com.municipalite.paris.entity.Reclamation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationWithCitoyenDto {
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
    
    // Informations du citoyen
    private CitoyenInfo citoyen;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CitoyenInfo {
        private Long id;
        private String nom;
        private String prenom;
        private String email;
        private String telephone;
    }
    
    public static ReclamationWithCitoyenDto fromReclamation(Reclamation reclamation) {
        ReclamationWithCitoyenDto dto = new ReclamationWithCitoyenDto();
        dto.setId(reclamation.getId());
        dto.setSujet(reclamation.getSujet());
        dto.setDescription(reclamation.getDescription());
        dto.setDateCreation(reclamation.getDateCreation());
        dto.setDateTraitement(reclamation.getDateTraitement());
        dto.setStatut(reclamation.getStatut().name());
        dto.setPriorite(reclamation.getPriorite().name());
        dto.setType(reclamation.getType().name());
        dto.setLocalisation(reclamation.getLocalisation());
        dto.setArrondissement(reclamation.getArrondissement());
        dto.setCommentaires(reclamation.getCommentaires());
        dto.setPieceJointe(reclamation.getPieceJointe());
        
        // Informations du citoyen
        if (reclamation.getCitoyen() != null) {
            CitoyenInfo citoyenInfo = new CitoyenInfo();
            citoyenInfo.setId(reclamation.getCitoyen().getId());
            citoyenInfo.setNom(reclamation.getCitoyen().getNom());
            citoyenInfo.setPrenom(reclamation.getCitoyen().getPrenom());
            citoyenInfo.setEmail(reclamation.getCitoyen().getEmail());
            citoyenInfo.setTelephone(reclamation.getCitoyen().getTelephone());
            dto.setCitoyen(citoyenInfo);
        }
        
        return dto;
    }
}
