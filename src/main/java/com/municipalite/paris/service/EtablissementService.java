package com.municipalite.paris.service;

import com.municipalite.paris.entity.Etablissement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EtablissementService {
    List<Etablissement> findAll();
    Page<Etablissement> findAll(Pageable pageable);
    Etablissement findById(Long id);
    Etablissement save(Etablissement etablissement);
    Etablissement update(Long id, Etablissement etablissement);
    void deleteById(Long id);
    Etablissement updateStatut(Long id, Etablissement.StatutEtablissement statut);
    Page<Etablissement> findByType(Etablissement.TypeEtablissement type, Pageable pageable);
    Page<Etablissement> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    Page<Etablissement> findByArrondissement(String arrondissement, Pageable pageable);
}


