package com.municipalite.paris.repository;

import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentMunicipalRepository extends JpaRepository<AgentMunicipal, Long> {
    
    Optional<AgentMunicipal> findByUsername(String username);
    
    Optional<AgentMunicipal> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Page<AgentMunicipal> findByStatut(AgentMunicipal.StatutAgent statut, Pageable pageable);
    
    Page<AgentMunicipal> findByMunicipalite(Municipalite municipalite, Pageable pageable);
    
    Page<AgentMunicipal> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    
    List<AgentMunicipal> findByMunicipaliteIdAndStatut(Long municipaliteId, AgentMunicipal.StatutAgent statut);
    
    Page<AgentMunicipal> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(
        String nom, String prenom, Pageable pageable);
    
    @Query("SELECT COUNT(a) FROM AgentMunicipal a WHERE a.municipalite.id = :municipaliteId AND a.statut = 'ACTIF'")
    long countActiveAgentsByMunicipaliteId(@Param("municipaliteId") Long municipaliteId);
    
    @Query("SELECT a FROM AgentMunicipal a ORDER BY a.noteSatisfaction DESC")
    List<AgentMunicipal> findTopAgentsByNoteSatisfaction(Pageable pageable);
}


