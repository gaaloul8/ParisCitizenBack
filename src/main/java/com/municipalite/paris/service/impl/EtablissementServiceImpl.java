package com.municipalite.paris.service.impl;

import com.municipalite.paris.entity.Etablissement;
import com.municipalite.paris.repository.EtablissementRepository;
import com.municipalite.paris.service.EtablissementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EtablissementServiceImpl implements EtablissementService {

    private final EtablissementRepository etablissementRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Etablissement> findAll() {
        return etablissementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findAll(Pageable pageable) {
        return etablissementRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Etablissement findById(Long id) {
        return etablissementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Établissement non trouvé avec l'ID: " + id));
    }

    @Override
    public Etablissement save(Etablissement etablissement) {
        return etablissementRepository.save(etablissement);
    }

    @Override
    public Etablissement update(Long id, Etablissement etablissement) {
        Etablissement existing = findById(id);
        existing.setNom(etablissement.getNom());
        existing.setType(etablissement.getType());
        existing.setAdresse(etablissement.getAdresse());
        existing.setTelephone(etablissement.getTelephone());
        existing.setEmail(etablissement.getEmail());
        existing.setHoraires(etablissement.getHoraires());
        existing.setDescription(etablissement.getDescription());
        existing.setSiteWeb(etablissement.getSiteWeb());
        existing.setLatitude(etablissement.getLatitude());
        existing.setLongitude(etablissement.getLongitude());
        return etablissementRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        etablissementRepository.deleteById(id);
    }

    @Override
    public Etablissement updateStatut(Long id, Etablissement.StatutEtablissement statut) {
        Etablissement etablissement = findById(id);
        etablissement.setStatut(statut);
        return etablissementRepository.save(etablissement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findByType(Etablissement.TypeEtablissement type, Pageable pageable) {
        return etablissementRepository.findByType(type, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findByMunicipaliteId(Long municipaliteId, Pageable pageable) {
        return etablissementRepository.findByMunicipaliteId(municipaliteId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findByArrondissement(String arrondissement, Pageable pageable) {
        return etablissementRepository.findByArrondissement(arrondissement, pageable);
    }
}


