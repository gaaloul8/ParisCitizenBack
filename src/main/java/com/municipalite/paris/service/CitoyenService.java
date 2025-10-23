package com.municipalite.paris.service;

import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CitoyenService {
    List<Citoyen> findAll();
    Page<Citoyen> findAll(Pageable pageable);
    Citoyen findById(Long id);
    Citoyen findByEmail(String email);
    Citoyen findByUsername(String username);
    Citoyen save(Citoyen citoyen);
    Citoyen update(Long id, Citoyen citoyen);
    void deleteById(Long id);
    Citoyen updateStatut(Long id, Citoyen.StatutCitoyen statut);
    Page<Citoyen> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    Page<Citoyen> findByStatut(Citoyen.StatutCitoyen statut, Pageable pageable);
    Long[] getProjetsParticipes(Long citoyenId);
    
    Page<Reclamation> getReclamationsByCitoyen(Long citoyenId, Pageable pageable);
}


