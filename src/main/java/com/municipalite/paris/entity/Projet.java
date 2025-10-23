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
@Table(name = "projets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreatedDate
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatutProjet statut = StatutProjet.BROUILLON;

    @Column(precision = 15, scale = 2)
    private BigDecimal budget = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    @JsonIgnore
    private AgentMunicipal responsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipalite_id")
    @JsonIgnore
    private Municipalite municipalite;

    @Column(length = 100)
    private String arrondissement;

    @Column(name = "nombre_participants")
    private Integer nombreParticipants = 0;

    @Column(length = 255)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String localisation;

    // Champs pour les propositions citoyennes
    @Column(columnDefinition = "TEXT")
    private String objectifs;

    @Column(columnDefinition = "TEXT")
    private String beneficiaires;

    @Column(columnDefinition = "TEXT")
    private String partenaires;

    @Column(name = "duree_estimee")
    private Integer dureeEstimee;

    @Column(length = 255)
    private String contact;

    @Column(length = 20)
    private String telephone;

    @Column(length = 50)
    private String categorie;

    // Relation Many-to-Many avec Citoyen
    @ManyToMany
    @JoinTable(
        name = "projets_citoyens",
        joinColumns = @JoinColumn(name = "projet_id"),
        inverseJoinColumns = @JoinColumn(name = "citoyen_id")
    )
    @JsonIgnore
    private List<Citoyen> participants = new ArrayList<>();

    // Relation avec le créateur (citoyen)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id")
    @JsonIgnore
    private Citoyen createur;

    public enum StatutProjet {
        PROPOSITION, BROUILLON, ACTIF, EN_COURS, PLANIFIE, TERMINE, SUSPENDU, REJETE
    }
}

