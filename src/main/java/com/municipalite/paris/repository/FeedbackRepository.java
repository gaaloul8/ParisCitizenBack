package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Feedback;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Citoyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    Page<Feedback> findByType(Feedback.TypeFeedback type, Pageable pageable);
    
    Page<Feedback> findByStatut(Feedback.StatutFeedback statut, Pageable pageable);
    
    Page<Feedback> findByCitoyen(Citoyen citoyen, Pageable pageable);
    
    Page<Feedback> findByCitoyenId(Long citoyenId, Pageable pageable);
    
    Page<Feedback> findByAgent(AgentMunicipal agent, Pageable pageable);
    
    Page<Feedback> findByAgentId(Long agentId, Pageable pageable);
    
    Page<Feedback> findByTypeAndReferenceId(Feedback.TypeFeedback type, Long referenceId, Pageable pageable);
    
    List<Feedback> findByTypeAndReferenceId(Feedback.TypeFeedback type, Long referenceId);
    
    @Query("SELECT AVG(f.note) FROM Feedback f WHERE f.agent.id = :agentId")
    Double getAverageNoteByAgentId(@Param("agentId") Long agentId);
    
    @Query("SELECT AVG(f.note) FROM Feedback f WHERE f.type = :type AND f.referenceId = :referenceId")
    Double getAverageNoteByTypeAndReferenceId(@Param("type") Feedback.TypeFeedback type, 
                                               @Param("referenceId") Long referenceId);
    
    @Query("SELECT f.note, COUNT(f) FROM Feedback f WHERE f.agent.id = :agentId GROUP BY f.note")
    List<Object[]> countFeedbacksByNoteAndAgentId(@Param("agentId") Long agentId);
    
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.citoyen.id = :citoyenId")
    long countByCitoyenId(@Param("citoyenId") Long citoyenId);
}


