package com.municipalite.paris.service.impl;

import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.ProjetRepository;
import com.municipalite.paris.service.AgentMunicipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentMunicipalServiceImpl implements AgentMunicipalService {

    private final AgentMunicipalRepository agentRepository;
    private final ProjetRepository projetRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<AgentMunicipal> findAll() {
        return agentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgentMunicipal> findAll(Pageable pageable) {
        return agentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public AgentMunicipal findById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public AgentMunicipal findByEmail(String email) {
        return agentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public AgentMunicipal findByUsername(String username) {
        return agentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec le username: " + username));
    }

    @Override
    public AgentMunicipal save(AgentMunicipal agent) {
        if (agentRepository.existsByEmail(agent.getEmail())) {
            throw new RuntimeException("Un agent avec cet email existe déjà");
        }
        if (agentRepository.existsByUsername(agent.getUsername())) {
            throw new RuntimeException("Un agent avec ce username existe déjà");
        }
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        return agentRepository.save(agent);
    }

    @Override
    public AgentMunicipal update(Long id, AgentMunicipal agent) {
        AgentMunicipal existing = findById(id);
        existing.setNom(agent.getNom());
        existing.setPrenom(agent.getPrenom());
        existing.setEmail(agent.getEmail());
        existing.setTelephone(agent.getTelephone());
        existing.setPoste(agent.getPoste());
        if (agent.getPassword() != null && !agent.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(agent.getPassword()));
        }
        return agentRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        AgentMunicipal agent = findById(id);
        
        // 1. Supprimer les projets dont cet agent est responsable (mettre responsable à null)
        List<Projet> projetsResponsables = projetRepository.findByResponsableIdList(id);
        for (Projet projet : projetsResponsables) {
            projet.setResponsable(null);
            projetRepository.save(projet);
        }
        
        // 2. Supprimer l'agent (les réclamations traitées seront supprimées automatiquement grâce à cascade)
        agentRepository.deleteById(id);
    }

    @Override
    public AgentMunicipal updateStatut(Long id, AgentMunicipal.StatutAgent statut) {
        AgentMunicipal agent = findById(id);
        agent.setStatut(statut);
        return agentRepository.save(agent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgentMunicipal> findByMunicipaliteId(Long municipaliteId, Pageable pageable) {
        return agentRepository.findByMunicipaliteId(municipaliteId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgentMunicipal> findByStatut(AgentMunicipal.StatutAgent statut, Pageable pageable) {
        return agentRepository.findByStatut(statut, pageable);
    }
}


