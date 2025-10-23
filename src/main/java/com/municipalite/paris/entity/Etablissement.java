package com.municipalite.paris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "etablissements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeEtablissement type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String adresse;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(length = 150)
    private String email;

    @Column(length = 255)
    private String horaires;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String arrondissement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipalite_id")
    @JsonIgnore
    private Municipalite municipalite;

    @Column(name = "site_web", length = 255)
    private String siteWeb;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatutEtablissement statut = StatutEtablissement.OUVERT;

    @CreatedDate
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    public enum TypeEtablissement {
        ECOLE, HOPITAL, MAIRIE, ASSOCIATION, CULTUREL, SPORTIF, SOCIAL
    }

    public enum StatutEtablissement {
        OUVERT, FERME, TEMPORAIREMENT_FERME
    }
}


