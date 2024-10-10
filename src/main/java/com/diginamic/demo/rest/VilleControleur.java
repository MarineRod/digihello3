package com.diginamic.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.service.VilleService;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
	@Autowired
    private VilleService villeService;

    // Récupérer toutes les villes en tant que VilleDto
    @GetMapping
    public List<VilleDto> getAllVilles() {
        return villeService.getAllVilles();
    }

    // Recherche de toutes les villes dont le nom commence par une chaîne de caractères donnée
    @GetMapping("/recherche-nom")
    public List<VilleDto> rechercherParNom(@RequestParam("prefix") String prefix) {
        return villeService.rechercherVillesParNomPrefixe(prefix);
    }

    // Recherche de toutes les villes dont le nombre d'habitants est supérieur à min
    @GetMapping("/recherche-nbHabitants")
    public List<VilleDto> rechercherParNbHabitants(@RequestParam("min") int min) {
        return villeService.rechercherVillesParNbHabitantsMin(min);
    }

    // Recherche de toutes les villes dont le nombre d'habitants est supérieur à min et inférieur à max
    @GetMapping("/recherche-nbHabitants-min-max")
    public List<VilleDto> rechercherParNbHabitantsMinEtMax(@RequestParam("min") int min, @RequestParam("max") int max) {
        return villeService.rechercherVillesParNbHabitantsMinEtMax(min, max);
    }

    // Recherche des villes par code de département et un nombre d'habitants supérieur
    @GetMapping("/departement/{code}/habitant/superieur/{min}")
    public ResponseEntity<List<VilleDto>> getVillesByDepartementCodeAndNbHabitantsGreaterThan(
            @PathVariable String code,
            @PathVariable int min) {
        List<VilleDto> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThan(code, min);
        return ResponseEntity.ok(villes);
    }

    // Recherche des villes par code de département et nombre d'habitants dans une plage donnée
    @GetMapping("/departement/{code}/habitant/plage")
    public ResponseEntity<List<VilleDto>> getVillesByDepartementCodeAndNbHabitantsRange(
            @PathVariable String code,
            @RequestParam int min,
            @RequestParam int max) {
        List<VilleDto> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(code, min, max);
        return ResponseEntity.ok(villes);
    }
}