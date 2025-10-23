package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    
    Page<Reclamation> findByStatut(Reclamation.StatutReclamation statut, Pageable pageable);
    
    Page<Reclamation> findByPriorite(Reclamation.PrioriteReclamation priorite, Pageable pageable);
    
    Page<Reclamation> findByType(Reclamation.TypeReclamation type, Pageable pageable);
    
    Page<Reclamation> findByCitoyen(Citoyen citoyen, Pageable pageable);
    
    Page<Reclamation> findByCitoyenId(Long citoyenId, Pageable pageable);
    
    Page<Reclamation> findByAgent(AgentMunicipal agent, Pageable pageable);
    
    Page<Reclamation> findByAgentId(Long agentId, Pageable pageable);
    
    Page<Reclamation> findByMunicipalite(Municipalite municipalite, Pageable pageable);
    
    Page<Reclamation> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    
    Page<Reclamation> findByArrondissement(String arrondissement, Pageable pageable);
    
    Page<Reclamation> findBySujetContainingIgnoreCase(String sujet, Pageable pageable);
    
    @Query("SELECT r FROM Reclamation r WHERE r.municipalite.id = :municipaliteId AND r.statut = :statut")
    List<Reclamation> findByMunicipaliteIdAndStatut(@Param("municipaliteId") Long municipaliteId, 
                                                      @Param("statut") Reclamation.StatutReclamation statut);
    
    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.citoyen.id = :citoyenId")
    long countByCitoyenId(@Param("citoyenId") Long citoyenId);
    
    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.agent.id = :agentId AND r.statut = 'TRAITEE'")
    long countTraitedReclamationsByAgentId(@Param("agentId") Long agentId);
    
    @Query("SELECT r FROM Reclamation r WHERE r.dateCreation BETWEEN :startDate AND :endDate")
    List<Reclamation> findByDateCreationBetween(@Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT r.type, COUNT(r) FROM Reclamation r WHERE r.municipalite.id = :municipaliteId GROUP BY r.type")
    List<Object[]> countReclamationsByTypeAndMunicipalite(@Param("municipaliteId") Long municipaliteId);
    
    @Query("SELECT r.statut, COUNT(r) FROM Reclamation r WHERE r.municipalite.id = :municipaliteId GROUP BY r.statut")
    List<Object[]> countReclamationsByStatutAndMunicipalite(@Param("municipaliteId") Long municipaliteId);
}


