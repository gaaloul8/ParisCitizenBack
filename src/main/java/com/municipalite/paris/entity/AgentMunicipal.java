package com.municipalite.paris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agents_municipaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AgentMunicipal {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipalite_id")
    @JsonIgnore
    private Municipalite municipalite;

    @Column(length = 150)
    private String poste;

    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutAgent statut = StatutAgent.ACTIF;

    @Column(name = "nombre_reclamations_traitees")
    private Integer nombreReclamationsTraitees = 0;

    @Column(name = "note_satisfaction", precision = 3, scale = 2)
    private BigDecimal noteSatisfaction = BigDecimal.ZERO;

    @LastModifiedDate
    @Column(name = "derniere_activite")
    private LocalDateTime derniereActivite;

    // Relations
    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Projet> projetsGeres = new ArrayList<>();

    @OneToMany(mappedBy = "agent")
    @JsonIgnore
    private List<Reclamation> reclamationsTraitees = new ArrayList<>();

    @OneToMany(mappedBy = "agent")
    @JsonIgnore
    private List<Feedback> feedbacks = new ArrayList<>();

    public enum StatutAgent {
        ACTIF, INACTIF, SUSPENDU
    }
}


