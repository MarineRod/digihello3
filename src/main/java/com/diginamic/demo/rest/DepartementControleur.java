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
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.service.DepartementService;

@RestController
@RequestMapping("/departements")
@Validated
public class DepartementControleur {
	@Autowired
    private DepartementService departementService;

    
    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.findAll();
    }

    // 2. Récupère un département par ID
    @GetMapping("/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {
        Departement departement = departementService.findById(id);
        return ResponseEntity.ok(departement);
    }

    // 3. Crée un nouveau département
    @PostMapping
    public ResponseEntity<Departement> createDepartement(@RequestBody Departement departement) {
        Departement createdDepartement = departementService.save(departement);
        return ResponseEntity.status(201).body(createdDepartement);
    }

    // 4. Met à jour un département existant
    @PutMapping("/{id}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable int id, @RequestBody Departement departement) {
        departement.setId(id);
        Departement updatedDepartement = departementService.save(departement);
        return ResponseEntity.ok(updatedDepartement);
    }

    // 5. Supprime un département par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
        departementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


