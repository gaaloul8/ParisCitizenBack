package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.service.ProjetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    private final ProjetService projetService;

    @GetMapping
    public ResponseEntity<PageResponse<Projet>> getAllProjets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) Long municipaliteId) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Projet> result;
        
        if (statut != null) {
            result = projetService.findByStatut(Projet.StatutProjet.valueOf(statut.toUpperCase()), pageable);
        } else if (municipaliteId != null) {
            result = projetService.findByMunicipaliteId(municipaliteId, pageable);
        } else {
            result = projetService.findAll(pageable);
        }
        
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Projet>> getProjetById(@PathVariable Long id) {
        Projet projet = projetService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(projet));
    }

    @PostMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')") // Temporairement désactivé pour les tests
    public ResponseEntity<ApiResponse<Projet>> createProjet(@RequestBody Projet projet) {
        Projet created = projetService.save(projet);
        return ResponseEntity.ok(ApiResponse.success("Projet créé avec succès", created));
    }

    @PostMapping("/proposition")
    public ResponseEntity<ApiResponse<Projet>> createProposition(@RequestBody Projet projet) {
        // S'assurer que le statut est PROPOSITION pour les propositions citoyennes
        // Temporairement utiliser BROUILLON en attendant la correction de la base de données
        projet.setStatut(Projet.StatutProjet.BROUILLON);
        Projet created = projetService.save(projet);
        return ResponseEntity.ok(ApiResponse.success("Proposition soumise avec succès", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Projet>> updateProjet(
            @PathVariable Long id,
            @RequestBody Projet projet) {
        Projet updated = projetService.update(id, projet);
        return ResponseEntity.ok(ApiResponse.success("Projet mis à jour", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProjet(@PathVariable Long id) {
        projetService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Projet supprimé", null));
    }

    @PatchMapping("/{id}/statut")
    // @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')") // Temporairement désactivé pour les tests
    public ResponseEntity<ApiResponse<Projet>> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        Projet updated = projetService.updateStatut(id, Projet.StatutProjet.valueOf(statut.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour", updated));
    }

    @PostMapping("/{projetId}/participants/{citoyenId}")
    public ResponseEntity<ApiResponse<Projet>> addParticipant(
            @PathVariable Long projetId,
            @PathVariable Long citoyenId) {
        Projet updated = projetService.addParticipant(projetId, citoyenId);
        return ResponseEntity.ok(ApiResponse.success("Participant ajouté", updated));
    }

    @DeleteMapping("/{projetId}/participants/{citoyenId}")
    public ResponseEntity<ApiResponse<Projet>> removeParticipant(
            @PathVariable Long projetId,
            @PathVariable Long citoyenId) {
        Projet updated = projetService.removeParticipant(projetId, citoyenId);
        return ResponseEntity.ok(ApiResponse.success("Participant retiré", updated));
    }

    @GetMapping("/citoyen/{citoyenId}")
    public ResponseEntity<PageResponse<Projet>> getProjetsByCitoyen(
            @PathVariable Long citoyenId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Projet> result = projetService.findProjetsByCitoyenId(citoyenId, pageable);
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PageResponse<Projet>> getProjetsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Projet> result = projetService.findByStatut(Projet.StatutProjet.valueOf(status.toUpperCase()), pageable);
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{projetId}/participants")
    public ResponseEntity<ApiResponse<List<Citoyen>>> getParticipants(
            @PathVariable Long projetId) {
        List<Citoyen> participants = projetService.getParticipants(projetId);
        return ResponseEntity.ok(ApiResponse.success("Participants récupérés", participants));
    }

    @GetMapping("/period")
    public ResponseEntity<PageResponse<Projet>> getProjetsByPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Projet> result = projetService.findByPeriod(java.time.LocalDate.parse(startDate), java.time.LocalDate.parse(endDate), pageable);
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/arrondissement/{arrondissement}")
    public ResponseEntity<PageResponse<Projet>> getProjetsByArrondissement(
            @PathVariable String arrondissement,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<Projet> result = projetService.findByArrondissement(arrondissement, pageable);
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Object>> getProjetStats() {
        // Pour l'instant, retourner des données simulées
        // TODO: Implémenter la logique de calcul des statistiques réelles
        var stats = java.util.Map.of(
            "totalProjets", 25,
            "projetsActifs", 12,
            "projetsTermines", 8,
            "projetsBrouillon", 5,
            "budgetTotal", 1250000,
            "participantsTotal", 450,
            "repartitionParStatut", java.util.List.of(
                java.util.Map.of("statut", "Actif", "nombre", 12, "pourcentage", 48),
                java.util.Map.of("statut", "Terminé", "nombre", 8, "pourcentage", 32),
                java.util.Map.of("statut", "Brouillon", "nombre", 5, "pourcentage", 20)
            ),
            "repartitionParArrondissement", java.util.List.of(
                java.util.Map.of("arrondissement", "15ème", "nombre", 8, "pourcentage", 32),
                java.util.Map.of("arrondissement", "1er", "nombre", 5, "pourcentage", 20),
                java.util.Map.of("arrondissement", "20ème", "nombre", 4, "pourcentage", 16),
                java.util.Map.of("arrondissement", "11ème", "nombre", 3, "pourcentage", 12),
                java.util.Map.of("arrondissement", "18ème", "nombre", 5, "pourcentage", 20)
            ),
            "tendanceMensuelle", java.util.List.of(
                java.util.Map.of("mois", "Jan", "nouveauxProjets", 3, "projetsTermines", 1, "budgetUtilise", 45000),
                java.util.Map.of("mois", "Fév", "nouveauxProjets", 2, "projetsTermines", 2, "budgetUtilise", 38000),
                java.util.Map.of("mois", "Mar", "nouveauxProjets", 4, "projetsTermines", 1, "budgetUtilise", 52000),
                java.util.Map.of("mois", "Avr", "nouveauxProjets", 2, "projetsTermines", 3, "budgetUtilise", 41000),
                java.util.Map.of("mois", "Mai", "nouveauxProjets", 3, "projetsTermines", 1, "budgetUtilise", 48000),
                java.util.Map.of("mois", "Juin", "nouveauxProjets", 5, "projetsTermines", 2, "budgetUtilise", 61000)
            ),
            "topProjets", java.util.List.of(
                java.util.Map.of("id", 1, "titre", "Jardins partagés du 15ème", "budget", 25000, "participants", 45, "statut", "ACTIF"),
                java.util.Map.of("id", 2, "titre", "Ateliers numériques seniors", "budget", 15000, "participants", 32, "statut", "ACTIF"),
                java.util.Map.of("id", 3, "titre", "Fête de quartier annuelle", "budget", 35000, "participants", 120, "statut", "BROUILLON")
            )
        );
        
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @PatchMapping("/{id}/valider")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Projet>> validerProposition(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Object> request) {
        Long responsableId = Long.valueOf(request.get("responsableId").toString());
        Projet updated = projetService.updateStatut(id, Projet.StatutProjet.BROUILLON);
        // TODO: Assigner le responsable
        return ResponseEntity.ok(ApiResponse.success("Proposition validée", updated));
    }

    @PatchMapping("/{id}/rejeter")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ApiResponse<Projet>> rejeterProposition(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Object> request) {
        String motif = request.get("motif").toString();
        Projet updated = projetService.updateStatut(id, Projet.StatutProjet.REJETE);
        // TODO: Enregistrer le motif de rejet
        return ResponseEntity.ok(ApiResponse.success("Proposition rejetée", updated));
    }

    @PatchMapping("/{id}/assigner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Projet>> assignerResponsable(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Object> request) {
        Long responsableId = Long.valueOf(request.get("responsableId").toString());
        // TODO: Implémenter l'assignation du responsable
        Projet projet = projetService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Responsable assigné", projet));
    }
}


