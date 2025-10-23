package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.service.AgentMunicipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AgentMunicipalController {

    private final AgentMunicipalService agentService;

    @GetMapping
    public ResponseEntity<PageResponse<AgentMunicipal>> getAllAgents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) Long municipaliteId,
            @RequestParam(required = false) String statut) {
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<AgentMunicipal> result;
        
        if (municipaliteId != null) {
            result = agentService.findByMunicipaliteId(municipaliteId, pageable);
        } else if (statut != null) {
            result = agentService.findByStatut(AgentMunicipal.StatutAgent.valueOf(statut.toUpperCase()), pageable);
        } else {
            result = agentService.findAll(pageable);
        }
        
        return ResponseEntity.ok(PageResponse.of(result.getContent(), result.getTotalElements(), page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgentMunicipal>> getAgentById(@PathVariable Long id) {
        AgentMunicipal agent = agentService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(agent));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AgentMunicipal>> createAgent(@RequestBody AgentMunicipal agent) {
        AgentMunicipal created = agentService.save(agent);
        return ResponseEntity.ok(ApiResponse.success("Agent créé avec succès", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AgentMunicipal>> updateAgent(
            @PathVariable Long id,
            @RequestBody AgentMunicipal agent) {
        AgentMunicipal updated = agentService.update(id, agent);
        return ResponseEntity.ok(ApiResponse.success("Agent mis à jour", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAgent(@PathVariable Long id) {
        agentService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Agent supprimé", null));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AgentMunicipal>> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        AgentMunicipal updated = agentService.updateStatut(id, AgentMunicipal.StatutAgent.valueOf(statut.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour", updated));
    }
}


