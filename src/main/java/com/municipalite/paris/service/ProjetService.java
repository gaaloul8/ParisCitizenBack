package com.municipalite.paris.service;

import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.dto.CreateProjetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProjetService {
    List<Projet> findAll();
    Page<Projet> findAll(Pageable pageable);
    Projet findById(Long id);
    Projet save(Projet projet);
    Projet update(Long id, Projet projet);
    void deleteById(Long id);
    Projet updateStatut(Long id, Projet.StatutProjet statut);
    Page<Projet> findByStatut(Projet.StatutProjet statut, Pageable pageable);
    Page<Projet> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    Page<Projet> findByResponsableId(Long responsableId, Pageable pageable);
    // Méthode supprimée car la relation Many-to-Many avec les citoyens a été supprimée
    // Page<Projet> findProjetsByCitoyenId(Long citoyenId, Pageable pageable);
    Page<Projet> findByArrondissement(String arrondissement, Pageable pageable);
    Page<Projet> findByPeriod(LocalDate startDate, LocalDate endDate, Pageable pageable);
    // Méthodes supprimées car la relation Many-to-Many avec les citoyens a été supprimée
    // Projet addParticipant(Long projetId, Long citoyenId);
    // Projet removeParticipant(Long projetId, Long citoyenId);
    // List<Citoyen> getParticipants(Long projetId);
    Projet createProjet(CreateProjetRequest request);
}


