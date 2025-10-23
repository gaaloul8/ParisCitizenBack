package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    
    Page<Projet> findByStatut(Projet.StatutProjet statut, Pageable pageable);
    
    Page<Projet> findByMunicipalite(Municipalite municipalite, Pageable pageable);
    
    Page<Projet> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    
    Page<Projet> findByResponsable(AgentMunicipal responsable, Pageable pageable);
    
    Page<Projet> findByResponsableId(Long responsableId, Pageable pageable);
    
    Page<Projet> findByArrondissement(String arrondissement, Pageable pageable);
    
    Page<Projet> findByTitreContainingIgnoreCase(String titre, Pageable pageable);
    
    @Query("SELECT p FROM Projet p WHERE p.municipalite.id = :municipaliteId AND p.statut = :statut")
    List<Projet> findByMunicipaliteIdAndStatut(@Param("municipaliteId") Long municipaliteId, 
                                                 @Param("statut") Projet.StatutProjet statut);
    
    @Query("SELECT COUNT(p) FROM Projet p WHERE p.municipalite.id = :municipaliteId AND p.statut IN ('ACTIF', 'EN_COURS')")
    long countActiveProjetsByMunicipaliteId(@Param("municipaliteId") Long municipaliteId);
    
    @Query("SELECT p FROM Projet p WHERE p.dateDebut <= :date AND p.dateFin >= :date AND p.statut = 'ACTIF'")
    List<Projet> findActiveProjectsAtDate(@Param("date") LocalDate date);
    
    @Query("SELECT p FROM Projet p JOIN p.participants c WHERE c.id = :citoyenId")
    Page<Projet> findProjetsByCitoyenId(@Param("citoyenId") Long citoyenId, Pageable pageable);
    
    @Query("SELECT p FROM Projet p WHERE p.dateDebut >= :startDate AND p.dateFin <= :endDate")
    Page<Projet> findByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
}


