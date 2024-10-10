package com.diginamic.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.demo.entite.Departement;
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

	// Méthode pour ajouter ou mettre à jour un département
	public Departement saveDepartement(Departement departement) {
		return departementRepository.save(departement);
	}

	// Méthode pour supprimer un département par ID
	public void deleteDepartement(int id) {
		departementRepository.deleteById(id);
	}
}