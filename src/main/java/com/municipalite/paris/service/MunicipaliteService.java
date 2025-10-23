package com.municipalite.paris.service;

import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MunicipaliteService {
    List<Municipalite> findAll();
    Page<Municipalite> findAll(Pageable pageable);
    Municipalite findById(Long id);
    Municipalite save(Municipalite municipalite);
    Municipalite update(Long id, Municipalite municipalite);
    void deleteById(Long id);
    Municipalite updateStatut(Long id, Municipalite.StatutMunicipalite statut);
    Page<Municipalite> findByStatut(Municipalite.StatutMunicipalite statut, Pageable pageable);
    Page<Municipalite> findByRegion(String region, Pageable pageable);
    List<Municipalite> findTopByTauxSatisfaction(int limit);
}


