package com.diginamic.demo.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.service.VilleService;



@RestController
@RequestMapping("/villes")
@Validated
public class VilleControleur {

	@Autowired
    private VilleService villeService;

    // 1. Liste toutes les villes
    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.findAll();
    }

    // 2. Récupère une ville par ID
    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Ville ville = villeService.findById(id);
        return ResponseEntity.ok(ville);
    }

    // 3. Récupère une ville par nom
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Ville> getVilleByNom(@PathVariable String nom) {
        return villeService.findByNom(nom)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // 4. Crée une nouvelle ville
    @PostMapping
    public ResponseEntity<Ville> createVille(@RequestBody Ville ville) {
        Ville createdVille = villeService.save(ville);
        return ResponseEntity.status(201).body(createdVille);
    }

    // 5. Met à jour une ville existante
    @PutMapping("/{id}")
    public ResponseEntity<Ville> updateVille(@PathVariable int id, @RequestBody Ville ville) {
        ville.setId(id);
        Ville updatedVille = villeService.save(ville);
        return ResponseEntity.ok(updatedVille);
    }

    // 6. Supprime une ville par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable int id) {
        villeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/departements/{departmentId}/largest/{n}")
    public ResponseEntity<List<Ville>> getTopNLargestCitiesByDepartment(
            @PathVariable int departmentId,
            @PathVariable int n) {
        List<Ville> villes = villeService.findTopNLargestCitiesByDepartment(departmentId, n);
        return ResponseEntity.ok(villes);
    }

    // Endpoint pour lister les villes ayant une population comprise entre min et max
    @GetMapping("/departements/{departmentId}/population")
    public ResponseEntity<List<Ville>> getCitiesByPopulationRangeAndDepartment(
            @RequestParam int minPopulation,
            @RequestParam int maxPopulation,
            @PathVariable int departmentId) {
        List<Ville> villes = villeService.findCitiesByPopulationRangeAndDepartment(minPopulation, maxPopulation, departmentId);
        return ResponseEntity.ok(villes);
    }
    
    
}
