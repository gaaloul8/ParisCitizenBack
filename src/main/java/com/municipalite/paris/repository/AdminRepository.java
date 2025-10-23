package com.municipalite.paris.repository;

import com.municipalite.paris.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    Optional<Admin> findByUsername(String username);
    
    Optional<Admin> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Page<Admin> findByStatut(Admin.StatutAdmin statut, Pageable pageable);
    
    Page<Admin> findByRole(Admin.RoleAdmin role, Pageable pageable);
    
    Page<Admin> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(
        String nom, String prenom, Pageable pageable);
}


