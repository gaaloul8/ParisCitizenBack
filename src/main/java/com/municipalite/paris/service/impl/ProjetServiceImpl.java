package com.municipalite.paris.service.impl;

import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.dto.CreateProjetRequest;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.repository.ProjetRepository;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.MunicipaliteRepository;
import com.municipalite.paris.service.ProjetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;
    private final CitoyenRepository citoyenRepository;
    private final AgentMunicipalRepository agentRepository;
    private final MunicipaliteRepository municipaliteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Projet> findAll() {
        return projetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findAll(Pageable pageable) {
        return projetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Projet findById(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID: " + id));
    }

    @Override
    public Projet save(Projet projet) {
        return projetRepository.save(projet);
    }

    @Override
    public Projet update(Long id, Projet projet) {
        Projet existing = findById(id);
        existing.setTitre(projet.getTitre());
        existing.setDescription(projet.getDescription());
        existing.setDateDebut(projet.getDateDebut());
        existing.setDateFin(projet.getDateFin());
        existing.setBudget(projet.getBudget());
        existing.setArrondissement(projet.getArrondissement());
        existing.setLocalisation(projet.getLocalisation());
        existing.setImage(projet.getImage());
        return projetRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        projetRepository.deleteById(id);
    }

    @Override
    public Projet updateStatut(Long id, Projet.StatutProjet statut) {
        Projet projet = findById(id);
        projet.setStatut(statut);
        return projetRepository.save(projet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findByStatut(Projet.StatutProjet statut, Pageable pageable) {
        return projetRepository.findByStatut(statut, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findByMunicipaliteId(Long municipaliteId, Pageable pageable) {
        return projetRepository.findByMunicipaliteId(municipaliteId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findByResponsableId(Long responsableId, Pageable pageable) {
        return projetRepository.findByResponsableId(responsableId, pageable);
    }

    // Méthode supprimée car la relation Many-to-Many avec les citoyens a été supprimée
    /*
    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findProjetsByCitoyenId(Long citoyenId, Pageable pageable) {
        return projetRepository.findProjetsByCitoyenId(citoyenId, pageable);
    }
    */

    // Méthodes supprimées car la relation Many-to-Many avec les citoyens a été supprimée
    /*
    @Override
    public Projet addParticipant(Long projetId, Long citoyenId) {
        Projet projet = findById(projetId);
        Citoyen citoyen = citoyenRepository.findById(citoyenId)
                .orElseThrow(() -> new RuntimeException("Citoyen non trouvé avec l'ID: " + citoyenId));
        
        if (!projet.getParticipants().contains(citoyen)) {
            projet.getParticipants().add(citoyen);
            projet.setNombreParticipants(projet.getParticipants().size());
        }
        return projetRepository.save(projet);
    }

    @Override
    public Projet removeParticipant(Long projetId, Long citoyenId) {
        Projet projet = findById(projetId);
        Citoyen citoyen = citoyenRepository.findById(citoyenId)
                .orElseThrow(() -> new RuntimeException("Citoyen non trouvé avec l'ID: " + citoyenId));
        
        projet.getParticipants().remove(citoyen);
        projet.setNombreParticipants(projet.getParticipants().size());
        return projetRepository.save(projet);
    }
    */

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findByArrondissement(String arrondissement, Pageable pageable) {
        return projetRepository.findByArrondissement(arrondissement, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findByPeriod(java.time.LocalDate startDate, java.time.LocalDate endDate, Pageable pageable) {
        return projetRepository.findByPeriod(startDate, endDate, pageable);
    }

    // Méthode supprimée car la relation Many-to-Many avec les citoyens a été supprimée
    /*
    @Override
    @Transactional(readOnly = true)
    public List<Citoyen> getParticipants(Long projetId) {
        Projet projet = findById(projetId);
        return new ArrayList<>(projet.getParticipants());
    }
    */

    @Override
    public Projet createProjet(CreateProjetRequest request) {
        // Récupérer l'agent par défaut (agent@paris.fr)
        AgentMunicipal agent = agentRepository.findByEmail("agent@paris.fr")
                .orElseThrow(() -> new RuntimeException("Agent par défaut non trouvé"));
        
        // Récupérer la municipalité par défaut (ID 1)
        Municipalite municipalite = municipaliteRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Municipalité par défaut non trouvée"));
        
        // Créer le projet
        Projet projet = new Projet();
        projet.setTitre(request.getTitre());
        projet.setDescription(request.getDescription());
        projet.setDateDebut(request.getDateDebut());
        projet.setDateFin(request.getDateFin());
        projet.setBudget(request.getBudget());
        projet.setArrondissement(request.getArrondissement());
        projet.setLocalisation(request.getLocalisation());
        projet.setObjectifs(request.getObjectifs());
        projet.setBeneficiaires(request.getBeneficiaires());
        projet.setStatut(Projet.StatutProjet.BROUILLON);
        projet.setResponsable(agent);
        projet.setMunicipalite(municipalite);
        projet.setNombreParticipants(0);
        
        return projetRepository.save(projet);
    }
}


