package com.municipalite.paris.dto;

import com.municipalite.paris.entity.Reclamation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReclamationRequest {
    private String sujet;
    private String description;
    private String type;
    private String localisation;
    private String arrondissement;
    private String priorite;
    private String statut;
    private Long citoyenId;
    private String commentaires;
    private String pieceJointe;

    // Méthode pour convertir en entité Reclamation
    public Reclamation toReclamation() {
        Reclamation reclamation = new Reclamation();
        reclamation.setSujet(this.sujet);
        reclamation.setDescription(this.description);
        reclamation.setLocalisation(this.localisation);
        reclamation.setArrondissement(this.arrondissement);
        reclamation.setCommentaires(this.commentaires);
        reclamation.setPieceJointe(this.pieceJointe);
        
        // Conversion des enums
        if (this.type != null) {
            reclamation.setType(Reclamation.TypeReclamation.valueOf(this.type));
        }
        if (this.priorite != null) {
            reclamation.setPriorite(Reclamation.PrioriteReclamation.valueOf(this.priorite));
        }
        if (this.statut != null) {
            reclamation.setStatut(Reclamation.StatutReclamation.valueOf(this.statut));
        }
        
        return reclamation;
    }
}