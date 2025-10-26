package com.municipalite.paris.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateMunicipaliteRequest {
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String nom;
    
    @NotBlank(message = "La région est obligatoire")
    @Size(max = 100, message = "La région ne peut pas dépasser 100 caractères")
    private String region;
    
    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir exactement 5 chiffres")
    private String codePostal;
    
    @NotNull(message = "Le budget annuel est obligatoire")
    @DecimalMin(value = "0.0", message = "Le budget annuel doit être positif")
    private BigDecimal budgetAnnuel;
    
    @Size(max = 500, message = "L'adresse ne peut pas dépasser 500 caractères")
    private String adresse;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Format de téléphone invalide")
    private String telephone;
    
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne peut pas dépasser 150 caractères")
    private String email;
    
    @Size(max = 255, message = "L'URL du site web ne peut pas dépasser 255 caractères")
    private String siteWeb;
}
