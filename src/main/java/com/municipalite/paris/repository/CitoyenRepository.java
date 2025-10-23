package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Municipalite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitoyenRepository extends JpaRepository<Citoyen, Long> {
    
    Optional<Citoyen> findByUsername(String username);
    
    Optional<Citoyen> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Page<Citoyen> findByStatut(Citoyen.StatutCitoyen statut, Pageable pageable);
    
    Page<Citoyen> findByMunicipalite(Municipalite municipalite, Pageable pageable);
    
    Page<Citoyen> findByMunicipaliteId(Long municipaliteId, Pageable pageable);
    
    Page<Citoyen> findByCommune(String commune, Pageable pageable);
    
    Page<Citoyen> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(
        String nom, String prenom, Pageable pageable);
    
    @Query("SELECT COUNT(c) FROM Citoyen c WHERE c.municipalite.id = :municipaliteId AND c.statut = 'ACTIF'")
    long countActiveCitoyensByMunicipaliteId(@Param("municipaliteId") Long municipaliteId);
    
    @Query("SELECT AVG(c.age) FROM Citoyen c WHERE c.statut = 'ACTIF'")
    Double getAverageAge();
    
    @Query("SELECT c FROM Citoyen c WHERE c.age BETWEEN :minAge AND :maxAge")
    Page<Citoyen> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge, Pageable pageable);
}


