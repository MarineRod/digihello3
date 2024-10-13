package com.diginamic.demo.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.exception.CustomValidationException;
import com.diginamic.demo.exception.VilleNotFoundException;
import com.diginamic.demo.mapper.VilleMapper;
import com.diginamic.demo.service.VilleService;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

	@Autowired
	private VilleService villeService;

	@Autowired
	private VilleMapper villeMapper;

	// Récupérer toutes les villes en tant que VilleDto
	@GetMapping
	public ResponseEntity<List<VilleDto>> getAllVilles() {
		List<Ville> villes = villeService.getAllVilles();
		List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(villeDtos);
	}

	// Recherche de toutes les villes dont le nom commence par une chaine de
	// caractères
	// données
	@GetMapping("/recherche")
	public ResponseEntity<List<VilleDto>> rechercherVillesParNomPrefixe(@RequestParam String prefix)
			throws VilleNotFoundException {
		List<Ville> villes = villeService.rechercherVillesParNomPrefixe(prefix);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville dont le nom commence par '" + prefix + "' n'a été trouvée.");
		}
		List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(villeDtos);
	}

	// Recherche des villes avec un nombre d'habitants supérieur à min avec VilleDto
	@GetMapping("/habitants")
	public ResponseEntity<List<VilleDto>> rechercherVillesParNbHabitantsMin(@RequestParam int min)
			throws VilleNotFoundException {
		List<Ville> villes = villeService.rechercherVillesParNbHabitantsMin(min);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n'a une population supérieure à " + min + ".");
		}
		List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(villeDtos);
	}

	// Recherche des villes avec un nombre d'habitants entre min et max avec
	// VilleDto
	 @GetMapping("/habitants/min-max")
	    public ResponseEntity<List<VilleDto>> rechercherVillesParNbHabitantsMinEtMax(@RequestParam int min,
	                                                                                   @RequestParam int max) throws VilleNotFoundException {
	        List<Ville> villes = villeService.rechercherVillesParNbHabitantsMinEtMax(min, max);
	        if (villes.isEmpty()) {
	            throw new VilleNotFoundException("Aucune ville n'a une population comprise entre " + min + " et " + max + ".");
	        }
	        List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
	        return ResponseEntity.ok(villeDtos);
	    }

	// Créer une nouvelle ville
	 @PostMapping
	 public ResponseEntity<VilleDto> createVille(@RequestBody VilleDto villeDto) {
	     Ville ville = villeMapper.toEntity(villeDto);
	     try {
	         Ville savedVille = villeService.createVille(ville);
	         VilleDto savedVilleDto = villeMapper.toDto(savedVille);
	         return ResponseEntity.status(HttpStatus.CREATED).body(savedVilleDto);
	     } catch (CustomValidationException e) {
	         return ResponseEntity.badRequest().body(new VilleDto(e.getMessage(), 0, 0, null, null));
	     }
	 }

	// Modifier une nouvelle ville
	 @PutMapping("/{id}")
	 public ResponseEntity<VilleDto> updateVille(@PathVariable int id, @RequestBody VilleDto villeDto) {
	     Ville ville = villeMapper.toEntity(villeDto);
	     ville.setId(id); // Assigner l'ID à la ville à mettre à jour
	     try {
	         Ville updatedVille = villeService.updateVille(ville);
	         VilleDto updatedVilleDto = villeMapper.toDto(updatedVille);
	         return ResponseEntity.ok(updatedVilleDto);
	     } catch (CustomValidationException e) {
	         return ResponseEntity.badRequest().body(new VilleDto(e.getMessage(), id, id, null, null));
	     }
	 }

	// Supprimer une ville
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVille(@PathVariable int id) {
		villeService.deleteVille(id); // Appel à la méthode de service pour supprimer la ville
		return ResponseEntity.noContent().build(); // Renvoie un statut 204 No Content
	}

	// Recherche des villes d’un département dont la population est supérieure à min
	@GetMapping("/departement/{codeDept}/habitants")
	public ResponseEntity<List<Ville>> rechercherVillesParDepartementEtNbHabitantsMin(@PathVariable String codeDept,
			@RequestParam int min) throws VilleNotFoundException {
		List<Ville> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThan(codeDept, min);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException(
					"Aucune ville n’a une population supérieure à " + min + " dans le département " + codeDept + ".");
		}
		return ResponseEntity.ok(villes);
	}

	// Recherche des villes d’un département dont la population est supérieure à min
	// et inférieure à max
	@GetMapping("/departement/{codeDept}/habitants/min-max")
	public ResponseEntity<List<Ville>> rechercherVillesParDepartementEtNbHabitantsMinEtMax(
			@PathVariable String codeDept, @RequestParam int min, @RequestParam int max) throws VilleNotFoundException {
		List<Ville> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(codeDept,
				min, max);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + min + " et " + max
					+ " dans le département " + codeDept + ".");
		}
		return ResponseEntity.ok(villes);
	}

//	// Recherche des n villes les plus peuplées d’un département donné
//	@GetMapping("/departement/{codeDept}/top/{n}")
//	public ResponseEntity<List<Ville>> rechercherTopNVillesParDepartement(@PathVariable String codeDept,
//			@PathVariable int n) throws VilleNotFoundException {
//		List<Ville> villes = villeService.rechercherTopNVillesParDepartement(codeDept, n);
//		if (villes.isEmpty()) {
//			throw new VilleNotFoundException("Aucune ville n’a été trouvée pour le département " + codeDept + ".");
//		}
//		return ResponseEntity.ok(villes);
//	}

}