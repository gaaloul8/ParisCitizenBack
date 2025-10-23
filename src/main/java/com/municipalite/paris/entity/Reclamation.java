package com.municipalite.paris.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "reclamations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String sujet;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @CreatedDate
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutReclamation statut = StatutReclamation.NOUVELLE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioriteReclamation priorite = PrioriteReclamation.MOYENNE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeReclamation type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citoyen_id", nullable = false)
    @JsonIgnore
    private Citoyen citoyen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    @JsonIgnore
    private AgentMunicipal agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipalite_id")
    @JsonIgnore
    private Municipalite municipalite;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String localisation;

    @Column(length = 100)
    private String arrondissement;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    @Column(name = "piece_jointe", length = 255)
    private String pieceJointe;

    public enum StatutReclamation {
        NOUVELLE, EN_ATTENTE, EN_COURS, TRAITEE, REJETEE
    }

    public enum PrioriteReclamation {
        BASSE, MOYENNE, HAUTE, URGENTE
    }

    public enum TypeReclamation {
        VOIRIE, ECLAIRAGE, DECHETS, ESPACES_VERTS, TRANSPORT, AUTRE
    }
}


