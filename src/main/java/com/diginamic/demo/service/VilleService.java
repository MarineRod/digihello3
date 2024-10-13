package com.diginamic.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.exception.CustomValidationException;
import com.diginamic.demo.repository.VilleRepository;

@Service
public class VilleService {

	@Autowired
	private VilleRepository villeRepository;

	// Récupérer toutes les villes
	public List<Ville> getAllVilles() {
		return villeRepository.findAll();
	}

	// Recherche des villes par nom
	public List<Ville> rechercherVillesParNomPrefixe(String prefix) {
		return villeRepository.findByNomStartingWith(prefix);
	}

	// Recherche des villes avec un nombre d'habitants supérieur à min
	public List<Ville> rechercherVillesParNbHabitantsMin(int min) {
		return villeRepository.findByNbHabitantsGreaterThan(min);
	}

	// Recherche des villes avec un nombre d'habitants entre min et max
	public List<Ville> rechercherVillesParNbHabitantsMinEtMax(int min, int max) {
		return villeRepository.findByNbHabitantsGreaterThanAndNbHabitantsLessThan(min, max);
	}

	// Méthode pour trouver les villes par code de département et un nombre
	// d'habitants supérieur
	public List<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String code, int min) {
		return villeRepository.findByDepartementCodeAndNbHabitantsGreaterThan(code, min);
	}

	// Méthode pour trouver les villes par code de département avec un nombre
	// d'habitants entre min et max
	public List<Ville> findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(String code, int min,
			int max) {
		return villeRepository.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(code, min, max);
	}

	// Création d'une nouvelle ville avec validation
	public Ville createVille(Ville ville) throws CustomValidationException {
		// Appel des validations avant l'insertion
		validateVille(ville);
		return villeRepository.save(ville);
	}

	// Mise à jour d'une ville existante avec validation
	public Ville updateVille(Ville ville) throws CustomValidationException {
		// Appel des validations avant la mise à jour
		validateVille(ville);
		return villeRepository.save(ville);
	}

	// Validation des règles pour une Ville
	private void validateVille(Ville ville) throws CustomValidationException {
		// 1. La ville doit avoir au moins 10 habitants
		if (ville.getNbHabitants() < 10) {
			throw new CustomValidationException("La ville doit avoir au moins 10 habitants.");
		}

		// 2. Le nom de la ville doit contenir au moins 2 lettres
		if (ville.getNom() == null || ville.getNom().length() < 2) {
			 throw new CustomValidationException("Le nom de la ville doit contenir au moins 2 lettres.");
		}

		// 3. Le code département doit comporter exactement 2 caractères
		if (ville.getDepartement().getCode() == null || ville.getDepartement().getCode().length() != 2) {
			throw new CustomValidationException("Le code département doit comporter exactement 2 caractères.");
		}

//	        // 4. Le nom de la ville doit être unique pour un département donné
//	        Optional<Ville> existingVille = villeRepository.findByNomAndCodeDepartement(ville.getNom(), ville.getDepartement());
//	        if (existingVille.isPresent() && existingVille.get().getId() != ville.getId()) {
//	            return; // Remplacez par une logique alternative si nécessaire
//	        }
	}

	// Suppression d'une ville
	public void deleteVille(int id) {
		// Vérifier si la ville existe avant de la supprimer
		if (villeRepository.existsById(id)) {
			villeRepository.deleteById(id);
		}
	}

	// Recherche des n villes les plus peuplées d’un département donné
	public List<Ville> rechercherTopNVillesParDepartement(String codeDept, int n) {
		Pageable topN = PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "nbHabitants"));
		return villeRepository.findByDepartementCode(codeDept, topN);
	}
}