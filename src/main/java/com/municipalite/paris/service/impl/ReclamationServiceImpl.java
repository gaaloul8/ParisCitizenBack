package com.municipalite.paris.service.impl;

import com.municipalite.paris.dto.CreateReclamationRequest;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.repository.ReclamationRepository;
import com.municipalite.paris.service.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReclamationServiceImpl implements ReclamationService {

    private final ReclamationRepository reclamationRepository;
    private final AgentMunicipalRepository agentRepository;
    private final CitoyenRepository citoyenRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reclamation> findAll() {
        return reclamationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamation> findAll(Pageable pageable) {
        return reclamationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Reclamation findById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'ID: " + id));
    }

    @Override
    public Reclamation save(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation createReclamation(CreateReclamationRequest request) {
        // Récupérer le citoyen
        Citoyen citoyen = citoyenRepository.findById(request.getCitoyenId())
                .orElseThrow(() -> new RuntimeException("Citoyen non trouvé avec l'ID: " + request.getCitoyenId()));
        
        // Créer la réclamation
        Reclamation reclamation = new Reclamation();
        reclamation.setSujet(request.getSujet());
        reclamation.setDescription(request.getDescription());
        reclamation.setLocalisation(request.getLocalisation());
        reclamation.setArrondissement(request.getArrondissement());
        reclamation.setCommentaires(request.getCommentaires());
        reclamation.setPieceJointe(request.getPieceJointe());
        
        // Conversion des enums
        if (request.getType() != null) {
            reclamation.setType(Reclamation.TypeReclamation.valueOf(request.getType()));
        }
        if (request.getPriorite() != null) {
            reclamation.setPriorite(Reclamation.PrioriteReclamation.valueOf(request.getPriorite()));
        }
        if (request.getStatut() != null) {
            reclamation.setStatut(Reclamation.StatutReclamation.valueOf(request.getStatut()));
        }
        
        // Assigner le citoyen
        reclamation.setCitoyen(citoyen);
        
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation update(Long id, Reclamation reclamation) {
        Reclamation existing = findById(id);
        existing.setSujet(reclamation.getSujet());
        existing.setDescription(reclamation.getDescription());
        existing.setType(reclamation.getType());
        existing.setLocalisation(reclamation.getLocalisation());
        existing.setArrondissement(reclamation.getArrondissement());
        return reclamationRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        reclamationRepository.deleteById(id);
    }

    @Override
    public Reclamation updateStatut(Long id, Reclamation.StatutReclamation statut, String commentaires) {
        Reclamation reclamation = findById(id);
        reclamation.setStatut(statut);
        if (commentaires != null) {
            reclamation.setCommentaires(commentaires);
        }
        if (statut == Reclamation.StatutReclamation.TRAITEE) {
            reclamation.setDateTraitement(LocalDateTime.now());
        }
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation updatePriorite(Long id, Reclamation.PrioriteReclamation priorite) {
        Reclamation reclamation = findById(id);
        reclamation.setPriorite(priorite);
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation assignerAgent(Long id, Long agentId) {
        Reclamation reclamation = findById(id);
        AgentMunicipal agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'ID: " + agentId));
        
        reclamation.setAgent(agent);
        reclamation.setStatut(Reclamation.StatutReclamation.EN_COURS);
        return reclamationRepository.save(reclamation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamation> findByStatut(Reclamation.StatutReclamation statut, Pageable pageable) {
        return reclamationRepository.findByStatut(statut, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamation> findByCitoyenId(Long citoyenId, Pageable pageable) {
        return reclamationRepository.findByCitoyenId(citoyenId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamation> findByAgentId(Long agentId, Pageable pageable) {
        return reclamationRepository.findByAgentId(agentId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamation> findByMunicipaliteId(Long municipaliteId, Pageable pageable) {
        return reclamationRepository.findByMunicipaliteId(municipaliteId, pageable);
    }
}


