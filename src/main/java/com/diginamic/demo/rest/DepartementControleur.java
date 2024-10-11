package com.diginamic.demo.rest;

import java.util.List;
import java.util.stream.Collectors;

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

import com.diginamic.demo.dto.DepartementDto;
import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.mapper.DepartementMapper;
import com.diginamic.demo.service.DepartementService;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

	@Autowired
	private DepartementMapper departementMapper;
	
    @Autowired
    private DepartementService departementService;

    // Endpoint pour récupérer tous les départements
    @GetMapping
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<Departement> departements = departementService.getAllDepartements();
        List<DepartementDto> departementDtos = departements.stream()
                .map(departementMapper::toDto) // Utilisation de departementMapper
                .collect(Collectors.toList());
        return ResponseEntity.ok(departementDtos);
    }

    // Endpoint pour récupérer un département par ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) {
        Departement departement = departementService.getDepartementById(id);
        DepartementDto departementDto = departementMapper.toDto(departement); // Utilisation de departementMapper
        return ResponseEntity.ok(departementDto);
    }

    // Endpoint pour créer un nouveau département
    @PostMapping
    public ResponseEntity<DepartementDto> createDepartement(@RequestBody DepartementDto departementDto) {
        Departement departement = departementMapper.toEntity(departementDto); // Utilisation de departementMapper
        Departement createdDepartement = departementService.saveDepartement(departement);
        DepartementDto createdDepartementDto = departementMapper.toDto(createdDepartement); // Utilisation de departementMapper
        return ResponseEntity.status(201).body(createdDepartementDto);
    }

    // Endpoint pour mettre à jour un département existant
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDto> updateDepartement(@PathVariable int id, @RequestBody DepartementDto departementDto) {
        Departement departement = departementMapper.toEntity(departementDto); // Utilisation de departementMapper
        departement.setId(id);
        Departement updatedDepartement = departementService.saveDepartement(departement);
        DepartementDto updatedDepartementDto = departementMapper.toDto(updatedDepartement); // Utilisation de departementMapper
        return ResponseEntity.ok(updatedDepartementDto);
    }
    
    // Endpoint pour supprimer un département par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }

 
}