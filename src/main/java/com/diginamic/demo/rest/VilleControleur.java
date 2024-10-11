package com.diginamic.demo.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.service.VilleService;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleService villeService;

    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.getAllVilles();
    }
    // Recherche de toutes les villes dont le nom commence par une chaîne de caractères donnée
    @GetMapping("/recherche-nom")
    public List<Ville> rechercherParNom(@RequestParam("prefix") String prefix) {
        return villeService.rechercherVillesParNomPrefixe(prefix);
    }

    // Recherche de toutes les villes dont le nombre d'habitants est supérieur à min
    @GetMapping("/recherche-nbHabitants")
    public List<Ville> rechercherParNbHabitants(@RequestParam("min") int min) {
        return villeService.rechercherVillesParNbHabitantsMin(min);
    }

    // Recherche de toutes les villes dont le nombre d'habitants est supérieur à min et inférieur à max
    @GetMapping("/recherche-nbHabitants-min-max")
    public List<Ville> rechercherParNbHabitantsMinEtMax(@RequestParam("min") int min, @RequestParam("max") int max) {
        return villeService.rechercherVillesParNbHabitantsMinEtMax(min, max);
    }

    @GetMapping("/departement/{code}/habitant/superieur/{min}")
    public ResponseEntity<List<Ville>> getVillesByDepartementCodeAndNbHabitantsGreaterThan(
            @PathVariable String code,
            @PathVariable int min) {
        List<Ville> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThan(code, min);
        return ResponseEntity.ok(villes);
    }

    // Endpoint pour trouver les villes par code de département dans une plage de nombre d'habitants
    @GetMapping("/departement/{code}/habitant/plage")
    public ResponseEntity<List<Ville>> getVillesByDepartementCodeAndNbHabitantsRange(
            @PathVariable String code,
            @RequestParam int min,
            @RequestParam int max) {
        List<Ville> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(code, min, max);
        return ResponseEntity.ok(villes);
    }

    // Endpoint pour trouver les N villes par code de département, triées par nombre d'habitants
    @GetMapping("/departement/{code}/top")
    public ResponseEntity<Page<Ville>> getTopNVillesByDepartementCode(
            @PathVariable String code,
            Pageable pageable) {
        Page<Ville> villes = villeService.findTopNByDepartementCodeOrderByNbHabitantsDesc(code, pageable);
        return ResponseEntity.ok(villes);
    }
}