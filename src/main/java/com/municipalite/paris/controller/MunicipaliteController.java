package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.dto.CreateMunicipaliteRequest;
import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.service.MunicipaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/municipalites")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MunicipaliteController {

    private final MunicipaliteService municipaliteService;

    @GetMapping
    public ResponseEntity<PageResponse<Municipalite>> getAllMunicipalites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String region) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Municipalite> result;
        
        if (statut != null) {
            result = municipaliteService.findByStatut(Municipalite.StatutMunicipalite.valueOf(statut.toUpperCase()), pageable);
        } else if (region != null) {
            result = municipaliteService.findByRegion(region, pageable);
        } else {
            result = municipaliteService.findAll(pageable);
        }
        
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Municipalite>> getMunicipaliteById(@PathVariable Long id) {
        Municipalite municipalite = municipaliteService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(municipalite));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Municipalite>> createMunicipalite(@RequestBody CreateMunicipaliteRequest request) {
        Municipalite municipalite = new Municipalite();
        municipalite.setNom(request.getNom());
        municipalite.setRegion(request.getRegion());
        municipalite.setCodePostal(request.getCodePostal());
        municipalite.setBudgetAnnuel(request.getBudgetAnnuel());
        municipalite.setAdresse(request.getAdresse());
        municipalite.setTelephone(request.getTelephone());
        municipalite.setEmail(request.getEmail());
        municipalite.setSiteWeb(request.getSiteWeb());
        municipalite.setDateCreation(java.time.LocalDate.now());
        municipalite.setStatut(Municipalite.StatutMunicipalite.ACTIVE);
        
        Municipalite created = municipaliteService.save(municipalite);
        return ResponseEntity.ok(ApiResponse.success("Municipalité créée avec succès", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Municipalite>> updateMunicipalite(
            @PathVariable Long id,
            @RequestBody Municipalite municipalite) {
        Municipalite updated = municipaliteService.update(id, municipalite);
        return ResponseEntity.ok(ApiResponse.success("Municipalité mise à jour", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteMunicipalite(@PathVariable Long id) {
        municipaliteService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Municipalité supprimée", null));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Municipalite>> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        Municipalite updated = municipaliteService.updateStatut(id, Municipalite.StatutMunicipalite.valueOf(statut.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour", updated));
    }

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<List<Municipalite>>> getTopMunicipalites(
            @RequestParam(defaultValue = "5") int limit) {
        List<Municipalite> top = municipaliteService.findTopByTauxSatisfaction(limit);
        return ResponseEntity.ok(ApiResponse.success(top));
    }
}


