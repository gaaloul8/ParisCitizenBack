package com.municipalite.paris.controller;

import com.municipalite.paris.dto.response.ApiResponse;
import com.municipalite.paris.dto.response.PageResponse;
import com.municipalite.paris.dto.CreateAgentRequest;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.service.AgentMunicipalService;
import com.municipalite.paris.service.MunicipaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
public class AgentMunicipalController {

    private final AgentMunicipalService agentService;
    private final MunicipaliteService municipaliteService;

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
    public ResponseEntity<ApiResponse<AgentMunicipal>> createAgent(@RequestBody CreateAgentRequest request) {
        // Récupérer la municipalité
        Municipalite municipalite = municipaliteService.findById(request.getMunicipaliteId());
        
        // Créer l'agent
        AgentMunicipal agent = new AgentMunicipal();
        agent.setUsername(request.getUsername());
        agent.setPassword(request.getPassword()); // Le service va l'encoder
        agent.setNom(request.getNom());
        agent.setPrenom(request.getPrenom());
        agent.setEmail(request.getEmail());
        agent.setTelephone(request.getTelephone());
        agent.setMunicipalite(municipalite);
        agent.setPoste(request.getPoste());
        agent.setDateEmbauche(java.time.LocalDate.now());
        agent.setStatut(AgentMunicipal.StatutAgent.ACTIF);
        
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

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<List<AgentMunicipal>>> getTopAgents(
            @RequestParam(defaultValue = "5") int limit) {
        // Cette méthode pourrait être implémentée pour retourner les meilleurs agents
        return ResponseEntity.ok(ApiResponse.success(new ArrayList<>()));
    }
    
    @GetMapping("/municipalites")
    public ResponseEntity<ApiResponse<List<Municipalite>>> getMunicipalites() {
        List<Municipalite> municipalites = municipaliteService.findAll();
        return ResponseEntity.ok(ApiResponse.success(municipalites));
    }
}


