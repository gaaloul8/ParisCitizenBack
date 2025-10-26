package com.municipalite.paris.service.impl;

import com.municipalite.paris.entity.Citoyen;
import com.municipalite.paris.entity.Projet;
import com.municipalite.paris.entity.Reclamation;
import com.municipalite.paris.repository.CitoyenRepository;
import com.municipalite.paris.repository.ProjetRepository;
import com.municipalite.paris.service.CitoyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CitoyenServiceImpl implements CitoyenService {

    private final CitoyenRepository citoyenRepository;
    private final ProjetRepository projetRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Citoyen> findAll() {
        return citoyenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Citoyen> findAll(Pageable pageable) {
        return citoyenRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Citoyen findById(Long id) {
        return citoyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Citoyen non trouvé avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Citoyen findByEmail(String email) {
        return citoyenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Citoyen non trouvé avec l'email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Citoyen findByUsername(String username) {
        return citoyenRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Citoyen non trouvé avec le username: " + username));
    }

    @Override
    public Citoyen save(Citoyen citoyen) {
        if (citoyenRepository.existsByEmail(citoyen.getEmail())) {
            throw new RuntimeException("Un citoyen avec cet email existe déjà");
        }
        if (citoyenRepository.existsByUsername(citoyen.getUsername())) {
            throw new RuntimeException("Un citoyen avec ce username existe déjà");
        }
        citoyen.setPassword(passwordEncoder.encode(citoyen.getPassword()));
        return citoyenRepository.save(citoyen);
    }

    @Override
    public Citoyen update(Long id, Citoyen citoyen) {
        Citoyen existing = findById(id);
        existing.setNom(citoyen.getNom());
        existing.setPrenom(citoyen.getPrenom());
        existing.setEmail(citoyen.getEmail());
        existing.setTelephone(citoyen.getTelephone());
        existing.setAge(citoyen.getAge());
        existing.setAdresse(citoyen.getAdresse());
        existing.setCommune(citoyen.getCommune());
        if (citoyen.getPassword() != null && !citoyen.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(citoyen.getPassword()));
        }
        return citoyenRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        Citoyen citoyen = findById(id);
        
        // 1. Supprimer les projets créés par ce citoyen (mettre createur à null)
        List<Projet> projetsCrees = projetRepository.findByCreateurId(id);
        for (Projet projet : projetsCrees) {
            projet.setCreateur(null);
            projetRepository.save(projet);
        }
        
        // 2. Supprimer manuellement les entrées de la table projets_citoyens si elle existe encore
        try {
            // Cette requête SQL directe supprime les relations dans la table de liaison
            citoyenRepository.deleteProjetCitoyenRelations(id);
        } catch (Exception e) {
            // Si la table n'existe pas ou s'il y a une erreur, on continue
            System.out.println("Table projets_citoyens n'existe pas ou erreur lors de la suppression des relations: " + e.getMessage());
        }
        
        // 3. Supprimer le citoyen (les réclamations et feedbacks seront supprimés automatiquement grâce à cascade)
        citoyenRepository.deleteById(id);
    }

    @Override
    public Citoyen updateStatut(Long id, Citoyen.StatutCitoyen statut) {
        Citoyen citoyen = findById(id);
        citoyen.setStatut(statut);
        return citoyenRepository.save(citoyen);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Citoyen> findByMunicipaliteId(Long municipaliteId, Pageable pageable) {
        return citoyenRepository.findByMunicipaliteId(municipaliteId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Citoyen> findByStatut(Citoyen.StatutCitoyen statut, Pageable pageable) {
        return citoyenRepository.findByStatut(statut, pageable);
    }

    // Méthode supprimée car la relation Many-to-Many avec les projets a été supprimée
    /*
    @Override
    @Transactional(readOnly = true)
    public Long[] getProjetsParticipes(Long citoyenId) {
        Citoyen citoyen = findById(citoyenId);
        return citoyen.getProjetsParticipes().stream()
                .map(Projet::getId)
                .toArray(Long[]::new);
    }
    */

    @Override
    @Transactional(readOnly = true)
    public Page<Reclamation> getReclamationsByCitoyen(Long citoyenId, Pageable pageable) {
        Citoyen citoyen = findById(citoyenId);
        List<Reclamation> allReclamations = new ArrayList<>(citoyen.getReclamations());
        
        // Log pour debug
        System.out.println("Citoyen ID: " + citoyenId);
        System.out.println("Nombre de réclamations trouvées: " + allReclamations.size());
        
        if (allReclamations.isEmpty()) {
            return new org.springframework.data.domain.PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allReclamations.size());
        
        // Vérifier que start n'est pas plus grand que la taille
        if (start >= allReclamations.size()) {
            return new org.springframework.data.domain.PageImpl<>(new ArrayList<>(), pageable, allReclamations.size());
        }
        
        List<Reclamation> pageContent = allReclamations.subList(start, end);
        
        return new org.springframework.data.domain.PageImpl<>(pageContent, pageable, allReclamations.size());
    }
}


