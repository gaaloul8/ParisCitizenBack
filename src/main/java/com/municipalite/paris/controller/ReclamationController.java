package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.dto.CreateReclamationRequest;
import com.municipalite.paris.dto.ReclamationResponse;
import com.municipalite.paris.dto.ReclamationWithCitoyenDto;
import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.service.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reclamations")
@RequiredArgsConstructor
public class ReclamationController {

    private final ReclamationService reclamationService;

    @GetMapping
    public ResponseEntity<PageResponse<Reclamation>> getAllReclamations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) Long municipaliteId,
            @RequestParam(required = false) Long citoyenId) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Reclamation> result;
        
        if (citoyenId != null) {
            result = reclamationService.findByCitoyenId(citoyenId, pageable);
        } else if (statut != null) {
            result = reclamationService.findByStatut(Reclamation.StatutReclamation.valueOf(statut.toUpperCase()), pageable);
        } else if (municipaliteId != null) {
            result = reclamationService.findByMunicipaliteId(municipaliteId, pageable);
        } else {
            result = reclamationService.findAll(pageable);
        }
        
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Reclamation>> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(reclamation));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReclamationResponse>> createReclamation(@RequestBody CreateReclamationRequest request) {
        Reclamation created = reclamationService.createReclamation(request);
        ReclamationResponse response = ReclamationResponse.fromReclamation(created);
        return ResponseEntity.ok(ApiResponse.success("Réclamation créée avec succès", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Reclamation>> updateReclamation(
            @PathVariable Long id,
            @RequestBody Reclamation reclamation) {
        Reclamation updated = reclamationService.update(id, reclamation);
        return ResponseEntity.ok(ApiResponse.success("Réclamation mise à jour", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Réclamation supprimée", null));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Reclamation>> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut,
            @RequestParam(required = false) String commentaires) {
        Reclamation updated = reclamationService.updateStatut(
                id,
                Reclamation.StatutReclamation.valueOf(statut.toUpperCase()),
                commentaires
        );
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour", updated));
    }

    @PatchMapping("/{id}/priorite")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Reclamation>> updatePriorite(
            @PathVariable Long id,
            @RequestParam String priorite) {
        Reclamation updated = reclamationService.updatePriorite(
                id,
                Reclamation.PrioriteReclamation.valueOf(priorite.toUpperCase())
        );
        return ResponseEntity.ok(ApiResponse.success("Priorité mise à jour", updated));
    }

    @PatchMapping("/{id}/assigner")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Reclamation>> assignerAgent(
            @PathVariable Long id,
            @RequestParam Long agentId) {
        Reclamation updated = reclamationService.assignerAgent(id, agentId);
        return ResponseEntity.ok(ApiResponse.success("Agent assigné", updated));
    }

    @GetMapping("/agent/{agentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<PageResponse<ReclamationWithCitoyenDto>> getReclamationsByAgent(
            @PathVariable Long agentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Reclamation> result = reclamationService.findByAgentId(agentId, pageable);
        
        // Convertir les réclamations en DTO avec informations citoyen
        List<ReclamationWithCitoyenDto> dtoList = result.getContent().stream()
                .map(ReclamationWithCitoyenDto::fromReclamation)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(PageResponse.of(dtoList, result.getTotalElements(), page, limit));
    }
}


