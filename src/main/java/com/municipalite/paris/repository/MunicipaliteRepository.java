package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipaliteRepository extends JpaRepository<Municipalite, Long> {
    
    Page<Municipalite> findByStatut(Municipalite.StatutMunicipalite statut, Pageable pageable);
    
    Page<Municipalite> findByRegion(String region, Pageable pageable);
    
    Page<Municipalite> findByCodePostal(String codePostal, Pageable pageable);
    
    Page<Municipalite> findByNomContainingIgnoreCase(String nom, Pageable pageable);
    
    @Query("SELECT m FROM Municipalite m ORDER BY m.tauxSatisfaction DESC")
    List<Municipalite> findTopByOrderByTauxSatisfactionDesc(Pageable pageable);
    
    @Query("SELECT COUNT(m) FROM Municipalite m WHERE m.statut = 'ACTIVE'")
    long countActiveMunicipalites();
}

