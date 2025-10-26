package com.municipalite.paris.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAgentRequest {
    
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(max = 100, message = "Le nom d'utilisateur ne peut pas dépasser 100 caractères")
    private String username;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne peut pas dépasser 150 caractères")
    private String email;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Format de téléphone invalide")
    private String telephone;
    
    @NotNull(message = "La municipalité est obligatoire")
    private Long municipaliteId;
    
    @NotBlank(message = "Le poste est obligatoire")
    @Size(max = 150, message = "Le poste ne peut pas dépasser 150 caractères")
    private String poste;
}
