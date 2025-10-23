package com.municipalite.paris.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(length = 20)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleAdmin role = RoleAdmin.ADMIN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutAdmin statut = StatutAdmin.ACTIF;

    @CreatedDate
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "dernier_acces")
    private LocalDateTime dernierAcces;

    @LastModifiedDate
    @Column(name = "derniere_activite")
    private LocalDateTime derniereActivite;

    public enum RoleAdmin {
        SUPER_ADMIN, ADMIN
    }

    public enum StatutAdmin {
        ACTIF, INACTIF, SUSPENDU
    }
}


