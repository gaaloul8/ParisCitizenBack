package com.municipalite.paris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "municipalites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Municipalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nom;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false, length = 10)
    private String codePostal;

    @Column(name = "nombre_agents")
    private Integer nombreAgents = 0;

    @Column(name = "nombre_citoyens")
    private Integer nombreCitoyens = 0;

    @Column(name = "nombre_projets")
    private Integer nombreProjets = 0;

    @Column(name = "taux_satisfaction", precision = 5, scale = 2)
    private BigDecimal tauxSatisfaction = BigDecimal.ZERO;

    @Column(name = "budget_annuel", precision = 15, scale = 2)
    private BigDecimal budgetAnnuel = BigDecimal.ZERO;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutMunicipalite statut = StatutMunicipalite.ACTIVE;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(length = 20)
    private String telephone;

    @Column(length = 150)
    private String email;

    @Column(name = "site_web", length = 255)
    private String siteWeb;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Relations
    @OneToMany(mappedBy = "municipalite", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AgentMunicipal> agents = new ArrayList<>();

    @OneToMany(mappedBy = "municipalite", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Citoyen> citoyens = new ArrayList<>();

    @OneToMany(mappedBy = "municipalite", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Projet> projets = new ArrayList<>();

    @OneToMany(mappedBy = "municipalite", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reclamation> reclamations = new ArrayList<>();

    @OneToMany(mappedBy = "municipalite", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Etablissement> etablissements = new ArrayList<>();

    public enum StatutMunicipalite {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}


