package com.diginamic.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.repository.VilleRepository;
import org.springframework.data.domain.Pageable;


@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;
    
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

   
    public List<Ville> rechercherVillesParNomPrefixe(String prefix) {
        return villeRepository.findByNomStartingWith(prefix);
    }

   
    public List<Ville> rechercherVillesParNbHabitantsMin(int min) {
        return villeRepository.findByNbHabitantsGreaterThan(min);
    }

  
    public List<Ville> rechercherVillesParNbHabitantsMinEtMax(int min, int max) {
        return villeRepository.findByNbHabitantsGreaterThanAndNbHabitantsLessThan(min, max);
    }

  
 // Méthode pour trouver les villes par code de département et nombre d'habitants supérieur
    public List<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String code, int min) {
        return villeRepository.findByDepartementCodeAndNbHabitantsGreaterThan(code, min);
    }

    // Méthode pour trouver les villes par code de département avec un nombre d'habitants dans une plage
    public List<Ville> findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(String code, int min, int max) {
        return villeRepository.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(code, min, max);
    }

    // Méthode pour trouver les N villes par code de département, triées par nombre d'habitants décroissant
    public Page<Ville> findTopNByDepartementCodeOrderByNbHabitantsDesc(String code, Pageable pageable) {
        return villeRepository.findTopNByDepartementCodeOrderByNbHabitantsDesc(code, pageable);
    }
}