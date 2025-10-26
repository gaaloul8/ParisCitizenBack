package com.municipalite.paris.controller;

import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.entity.AgentMunicipal;
import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.repository.MunicipaliteRepository;
import com.municipalite.paris.repository.AgentMunicipalRepository;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestDataController {

    @Autowired
    private MunicipaliteRepository municipaliteRepository;
    
    @Autowired
    private AgentMunicipalRepository agentRepository;
    
    @Autowired
    private CitoyenRepository citoyenRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDataStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long municipalitesCount = municipaliteRepository.count();
        long agentsCount = agentRepository.count();
        long citoyensCount = citoyenRepository.count();
        
        stats.put("municipalites", municipalitesCount);
        stats.put("agents", agentsCount);
        stats.put("citoyens", citoyensCount);
        
        return ResponseEntity.ok(ApiResponse.success("Statistiques des données", stats));
    }
    
    @GetMapping("/municipalites")
    public ResponseEntity<ApiResponse<Iterable<Municipalite>>> getAllMunicipalites() {
        Iterable<Municipalite> municipalites = municipaliteRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Liste des municipalités", municipalites));
    }
    
    @GetMapping("/agents")
    public ResponseEntity<ApiResponse<Iterable<AgentMunicipal>>> getAllAgents() {
        Iterable<AgentMunicipal> agents = agentRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Liste des agents", agents));
    }
    
    @GetMapping("/citoyens")
    public ResponseEntity<ApiResponse<Iterable<Citoyen>>> getAllCitoyens() {
        Iterable<Citoyen> citoyens = citoyenRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Liste des citoyens", citoyens));
    }
}
