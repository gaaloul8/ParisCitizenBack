package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.entity.Etablissement;
import com.municipalite.paris.service.EtablissementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/etablissements")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EtablissementController {

    private final EtablissementService etablissementService;

    @GetMapping
    public ResponseEntity<PageResponse<Etablissement>> getAllEtablissements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String arrondissement) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Etablissement> result;
        
        if (type != null) {
            result = etablissementService.findByType(Etablissement.TypeEtablissement.valueOf(type.toUpperCase()), pageable);
        } else if (arrondissement != null) {
            result = etablissementService.findByArrondissement(arrondissement, pageable);
        } else {
            result = etablissementService.findAll(pageable);
        }
        
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Etablissement>> getEtablissementById(@PathVariable Long id) {
        Etablissement etablissement = etablissementService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(etablissement));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Etablissement>> createEtablissement(@RequestBody Etablissement etablissement) {
        Etablissement created = etablissementService.save(etablissement);
        return ResponseEntity.ok(ApiResponse.success("Établissement créé avec succès", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Etablissement>> updateEtablissement(
            @PathVariable Long id,
            @RequestBody Etablissement etablissement) {
        Etablissement updated = etablissementService.update(id, etablissement);
        return ResponseEntity.ok(ApiResponse.success("Établissement mis à jour", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEtablissement(@PathVariable Long id) {
        etablissementService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Établissement supprimé", null));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Etablissement>> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        Etablissement updated = etablissementService.updateStatut(
                id,
                Etablissement.StatutEtablissement.valueOf(statut.toUpperCase())
        );
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour", updated));
    }
}


