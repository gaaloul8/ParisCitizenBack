package com.municipalite.paris.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjetRequest {
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal budget;
    private String responsable;
    private String arrondissement;
    private String localisation;
    private String objectifs;
    private String beneficiaires;
}




