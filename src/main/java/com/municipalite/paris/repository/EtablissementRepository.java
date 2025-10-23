package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Etablissement;
import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
    
    Page<Etablissement> findByType(Etablissement.TypeEtablissement type, Pageable pageable);
    
    Page<Etablissement> findByStatut(Etablissement.StatutEtablissement statut, Pageable pageable);
    
    Page<Etablissement> findByMunicipalite(Municipalite municipalite, Pageable pageable);
    
    Page<Etablissement> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    
    Page<Etablissement> findByArrondissement(String arrondissement, Pageable pageable);
    
    Page<Etablissement> findByNomContainingIgnoreCase(String nom, Pageable pageable);
    
    Page<Etablissement> findByTypeAndArrondissement(Etablissement.TypeEtablissement type, 
                                                     String arrondissement, Pageable pageable);
    
    List<Etablissement> findByTypeAndStatut(Etablissement.TypeEtablissement type, 
                                             Etablissement.StatutEtablissement statut);
    
    @Query("SELECT COUNT(e) FROM Etablissement e WHERE e.municipalite.id = :municipaliteId AND e.statut = 'OUVERT'")
    long countOpenEtablissementsByMunicipaliteId(@Param("municipaliteId") Long municipaliteId);
    
    @Query("SELECT e.type, COUNT(e) FROM Etablissement e WHERE e.municipalite.id = :municipaliteId GROUP BY e.type")
    List<Object[]> countEtablissementsByTypeAndMunicipalite(@Param("municipaliteId") Long municipaliteId);
}


