package com.municipalite.paris.service.impl;

import com.municipalite.paris.entity.Municipalite;
import com.municipalite.paris.repository.MunicipaliteRepository;
import com.municipalite.paris.service.MunicipaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MunicipaliteServiceImpl implements MunicipaliteService {

    private final MunicipaliteRepository municipaliteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Municipalite> findAll() {
        return municipaliteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Municipalite> findAll(Pageable pageable) {
        return municipaliteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Municipalite findById(Long id) {
        return municipaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Municipalité non trouvée avec l'ID: " + id));
    }

    @Override
    public Municipalite save(Municipalite municipalite) {
        return municipaliteRepository.save(municipalite);
    }

    @Override
    public Municipalite update(Long id, Municipalite municipalite) {
        Municipalite existing = findById(id);
        existing.setNom(municipalite.getNom());
        existing.setRegion(municipalite.getRegion());
        existing.setCodePostal(municipalite.getCodePostal());
        existing.setBudgetAnnuel(municipalite.getBudgetAnnuel());
        existing.setAdresse(municipalite.getAdresse());
        existing.setTelephone(municipalite.getTelephone());
        existing.setEmail(municipalite.getEmail());
        existing.setSiteWeb(municipalite.getSiteWeb());
        existing.setStatut(municipalite.getStatut());
        return municipaliteRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        municipaliteRepository.deleteById(id);
    }

    @Override
    public Municipalite updateStatut(Long id, Municipalite.StatutMunicipalite statut) {
        Municipalite municipalite = findById(id);
        municipalite.setStatut(statut);
        return municipaliteRepository.save(municipalite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Municipalite> findByStatut(Municipalite.StatutMunicipalite statut, Pageable pageable) {
        return municipaliteRepository.findByStatut(statut, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Municipalite> findByRegion(String region, Pageable pageable) {
        return municipaliteRepository.findByRegion(region, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipalite> findTopByTauxSatisfaction(int limit) {
        return municipaliteRepository.findTopByOrderByTauxSatisfactionDesc(PageRequest.of(0, limit));
    }
}


