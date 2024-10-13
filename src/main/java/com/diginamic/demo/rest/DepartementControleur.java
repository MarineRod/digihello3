package com.diginamic.demo.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.diginamic.demo.exception.CustomValidationException;
import com.diginamic.demo.mapper.DepartementMapper;
import com.diginamic.demo.service.DepartementService;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

	@Autowired
	private DepartementService departementService;

	@Autowired
	private DepartementMapper departementMapper;

	// Récupérer tous les départements en tant que DepartementDto
	@GetMapping
	public ResponseEntity<List<DepartementDto>> getAllDepartements() {
		List<Departement> departements = departementService.getAllDepartements();
		List<DepartementDto> departementDtos = departements.stream().map(departementMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(departementDtos);
	}

	// Récupérer un département par ID
	@GetMapping("/{id}")
	public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) {
		Departement departement = departementService.getDepartementById(id);
		return ResponseEntity.ok(departementMapper.toDto(departement));
	}

	// Recherche d'un département par nom
	@GetMapping("/nom/{nom}")
	public ResponseEntity<DepartementDto> getDepartementByNom(@PathVariable String nom) {
		Optional<Departement> departement = departementService.getDepartementByNom(nom);
		return departement.map(d -> ResponseEntity.ok(departementMapper.toDto(d)))
				.orElse(ResponseEntity.notFound().build());
	}

	// Création d'un nouveau département
	@PostMapping
	public ResponseEntity<DepartementDto> createDepartement(@RequestBody DepartementDto departementDto) {
	    // Conversion de DepartementDto à Departement
	    Departement departement = departementMapper.toEntity(departementDto);
	    
	    try {
	        // Sauvegarde du département
	        Departement savedDepartement = departementService.saveDepartement(departement);
	        // Retourne le département sauvegardé en DTO avec un statut 201 (CREATED)
	        return ResponseEntity.status(HttpStatus.CREATED).body(departementMapper.toDto(savedDepartement));
	    } catch (CustomValidationException e) { 
	        // Retourne une réponse 400 (BAD REQUEST) avec un message d'erreur
	        return ResponseEntity.badRequest().body(new DepartementDto());
	    }
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<DepartementDto> updateDepartement(@PathVariable int id,
			@RequestBody DepartementDto departementDto) {
		try {
			Departement departement = departementMapper.toEntity(departementDto);
			Departement updatedDepartement = departementService.updateDepartement(id, departement);
			DepartementDto updatedDepartementDto = departementMapper.toDto(updatedDepartement);
			return ResponseEntity.ok(updatedDepartementDto);
		} catch (CustomValidationException e) {
			return ResponseEntity.badRequest().body(new DepartementDto());
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Suppression d'un département
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
		departementService.deleteDepartement(id);
		return ResponseEntity.noContent().build();
	}
}