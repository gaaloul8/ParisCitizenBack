package com.municipalite.paris.service;

import com.municipalite.paris.dto.CreateReclamationRequest;
import com.municipalite.paris.entity.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReclamationService {
    List<Reclamation> findAll();
    Page<Reclamation> findAll(Pageable pageable);
    Reclamation findById(Long id);
    Reclamation save(Reclamation reclamation);
    Reclamation createReclamation(CreateReclamationRequest request);
    Reclamation update(Long id, Reclamation reclamation);
    void deleteById(Long id);
    Reclamation updateStatut(Long id, Reclamation.StatutReclamation statut, String commentaires);
    Reclamation updatePriorite(Long id, Reclamation.PrioriteReclamation priorite);
    Reclamation assignerAgent(Long id, Long agentId);
    Page<Reclamation> findByStatut(Reclamation.StatutReclamation statut, Pageable pageable);
    Page<Reclamation> findByCitoyenId(Long citoyenId, Pageable pageable);
    Page<Reclamation> findByAgentId(Long agentId, Pageable pageable);
    Page<Reclamation> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
}


