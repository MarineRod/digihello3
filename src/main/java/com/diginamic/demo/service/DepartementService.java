package com.diginamic.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.exception.CustomValidationException;
import com.diginamic.demo.repository.DepartementRepository;

@Service
public class DepartementService {

	 @Autowired
	    private DepartementRepository departementRepository;

	    // Méthode pour récupérer tous les départements
	    public List<Departement> getAllDepartements() {
	        return departementRepository.findAll();
	    }

	    // Méthode pour récupérer un département par ID
	    public Departement getDepartementById(int id) {
	        return departementRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Departement non trouvé pour l'id : " + id));
	    }

	    // Méthode pour rechercher un département par nom
	    public Optional<Departement> getDepartementByNom(String nom) {
	        return departementRepository.findByNom(nom);
	    }

	    // Méthode pour supprimer un département par ID
	    public void deleteDepartement(int id) {
	        if (!departementRepository.existsById(id)) {
	            throw new RuntimeException("Le département avec l'ID " + id + " n'existe pas.");
	        }
	        departementRepository.deleteById(id);
	    }

	    // Validation des règles pour un département
	    private void validateDepartement(Departement departement) throws CustomValidationException {
	        // 1. Le code département doit comporter entre 2 et 3 caractères
	        if (departement.getCode() == null || departement.getCode().length() < 2 || departement.getCode().length() > 3) {
	            throw new CustomValidationException("Le code département doit comporter entre 2 et 3 caractères.");
	        }

	        // 2. Le nom du département est obligatoire et doit comporter au moins 3 lettres
	        if (departement.getNom() == null || departement.getNom().length() < 3) {
	            throw new CustomValidationException("Le nom du département doit comporter au moins 3 lettres.");
	        }

	        // 3. Le code département doit être unique
	        Optional<Departement> existingDepartement = departementRepository.findByCode(departement.getCode());
	        if (existingDepartement.isPresent() && existingDepartement.get().getId()!=(departement.getId())) {
	            throw new CustomValidationException("Le code du département doit être unique.");
	        }
	    }

	    // Méthode pour ajouter un département avec validation
	    public Departement saveDepartement(Departement departement) throws CustomValidationException {
	        validateDepartement(departement); // Validation avant l'insertion ou la mise à jour
	        return departementRepository.save(departement);
	    }
	    
	    public Departement updateDepartement(int id, Departement departement) throws CustomValidationException {
	        if (!departementRepository.existsById(id)) {
	            throw new RuntimeException("Le département avec l'ID " + id + " n'existe pas.");
	        }
	        departement.setId(id);
	        validateDepartement(departement);
	        return departementRepository.save(departement);
	    }
	}