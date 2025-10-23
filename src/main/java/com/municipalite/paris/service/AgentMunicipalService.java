package com.municipalite.paris.service;

import com.municipalite.paris.entity.AgentMunicipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgentMunicipalService {
    List<AgentMunicipal> findAll();
    Page<AgentMunicipal> findAll(Pageable pageable);
    AgentMunicipal findById(Long id);
    AgentMunicipal findByEmail(String email);
    AgentMunicipal findByUsername(String username);
    AgentMunicipal save(AgentMunicipal agent);
    AgentMunicipal update(Long id, AgentMunicipal agent);
    void deleteById(Long id);
    AgentMunicipal updateStatut(Long id, AgentMunicipal.StatutAgent statut);
    Page<AgentMunicipal> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    Page<AgentMunicipal> findByStatut(AgentMunicipal.StatutAgent statut, Pageable pageable);
}


