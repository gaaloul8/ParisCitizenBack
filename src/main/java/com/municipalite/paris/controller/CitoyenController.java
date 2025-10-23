package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.service.CitoyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/citoyens")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CitoyenController {

    private final CitoyenService citoyenService;

    @GetMapping
    public ResponseEntity<PageResponse<Citoyen>> getAllCitoyens(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) Long municipaliteId,
            @RequestParam(required = false) String statut) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Citoyen> result;
        
        if (municipaliteId != null) {
            result = citoyenService.findByMunicipaliteId(municipaliteId, pageable);
        } else if (statut != null) {
            result = citoyenService.findByStatut(Citoyen.StatutCitoyen.valueOf(statut.toUpperCase()), pageable);
        } else {
            result = citoyenService.findAll(pageable);
        }
        
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Citoyen>> getCitoyenById(@PathVariable Long id) {
        Citoyen citoyen = citoyenService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(citoyen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Citoyen>> updateCitoyen(
            @PathVariable Long id,
            @RequestBody Citoyen citoyen) {
        Citoyen updated = citoyenService.update(id, citoyen);
        return ResponseEntity.ok(ApiResponse.success("Profil mis à jour", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCitoyen(@PathVariable Long id) {
        citoyenService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Compte supprimé", null));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Citoyen>> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        Citoyen updated = citoyenService.updateStatut(id, Citoyen.StatutCitoyen.valueOf(statut.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour", updated));
    }

    @GetMapping("/{id}/projets-participes")
    public ResponseEntity<ApiResponse<Long[]>> getProjetsParticipes(@PathVariable Long id) {
        // Pour l'instant, permettre l'accès sans authentification pour les tests
        // TODO: Ajouter l'authentification JWT
        Long[] projetIds = citoyenService.getProjetsParticipes(id);
        return ResponseEntity.ok(ApiResponse.success("Projets participés récupérés", projetIds));
    }

    @GetMapping("/{id}/reclamations")
    public ResponseEntity<PageResponse<Reclamation>> getReclamationsByCitoyen(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Reclamation> result = citoyenService.getReclamationsByCitoyen(id, pageable);
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }
}


