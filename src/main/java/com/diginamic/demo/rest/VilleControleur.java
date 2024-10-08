package com.diginamic.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.demo.service.Ville;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/villes")
@Validated
public class VilleControleur {

	private List<Ville> villes = new ArrayList<>();

    private int idCounter = 1; // Démarre à 1 pour le premier ID

    // Constructeur pour initialiser les villes
    public VilleControleur() {
        villes.add(new Ville(idCounter++, "Paris", 2148000));
        villes.add(new Ville(idCounter++, "Lyon", 513275));
        villes.add(new Ville(idCounter++, "Marseille", 861635));
        villes.add(new Ville(idCounter++, "Toulouse", 479709));
        villes.add(new Ville(idCounter++, "Nice", 340017));
    }

	@GetMapping
	public List<Ville> getVilles() {
		return villes;
	}

	@PostMapping("/add")
	public ResponseEntity<String> ajouterVille(@Valid @RequestBody Ville nouvelleVille) {
		
		for (Ville ville : villes) {
			if (ville.getNom().equalsIgnoreCase(nouvelleVille.getNom())) {
				return new ResponseEntity<>("La ville existe déjà", HttpStatus.BAD_REQUEST);
			}
		}

		
		nouvelleVille.setId(idCounter++); 
		villes.add(nouvelleVille); 

		return new ResponseEntity<>("Ville insérée avec succès", HttpStatus.OK); // Code 200 (succès)
	}

	@PutMapping("/modify/{id}")
	public ResponseEntity<String> modifierVille(@PathVariable int id, @Valid @RequestBody Ville villeModifiee) {
	    // Vérifier si la ville existante est présente
	    Ville villeExistante = villes.stream()
	            .filter(ville -> ville.getId() == id)
	            .findFirst()
	            .orElse(null);

	    if (villeExistante == null) {
	        return new ResponseEntity<>("Ville non trouvée", HttpStatus.NOT_FOUND);
	    }

	    // Vérifier si une autre ville a les mêmes détails (nom et nbHabitants)
	    boolean detailsIdentiques = villes.stream()
	            .filter(ville -> ville.getId() != id) // Exclure la ville actuelle
	            .anyMatch(ville -> 
	                ville.getNom().equalsIgnoreCase(villeModifiee.getNom()) &&
	                ville.getNbHabitants() == villeModifiee.getNbHabitants()
	            );

	    if (detailsIdentiques) {
	        return new ResponseEntity<>("Une ville avec ces mêmes renseignements existe déjà", HttpStatus.BAD_REQUEST);
	    }

	    // Appliquer les modifications
	    villeExistante.setNom(villeModifiee.getNom());
	    villeExistante.setNbHabitants(villeModifiee.getNbHabitants());

	    return new ResponseEntity<>("Ville mise à jour avec succès", HttpStatus.OK);
	}


}
