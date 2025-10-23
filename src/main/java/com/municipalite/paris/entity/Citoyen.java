package com.municipalite.paris.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "citoyens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Citoyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String telephone;

    private Integer age;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(length = 100)
    private String commune;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipalite_id")
    @JsonIgnore
    private Municipalite municipalite;

    @CreatedDate
    @Column(name = "date_inscription", updatable = false)
    private LocalDateTime dateInscription;

    @Column(name = "nombre_reclamations")
    private Integer nombreReclamations = 0;

    @Column(name = "nombre_projets_participes")
    private Integer nombreProjetsParticipes = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutCitoyen statut = StatutCitoyen.ACTIF;

    @LastModifiedDate
    @Column(name = "derniere_activite")
    private LocalDateTime derniereActivite;

    // Relations
    @OneToMany(mappedBy = "citoyen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reclamation> reclamations = new ArrayList<>();

    @OneToMany(mappedBy = "citoyen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    private List<Projet> projetsParticipes = new ArrayList<>();

    public enum StatutCitoyen {
        ACTIF, INACTIF, SUSPENDU
    }
}


