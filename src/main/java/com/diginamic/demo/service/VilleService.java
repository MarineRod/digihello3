package com.diginamic.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.mapper.VilleMapper;
import com.diginamic.demo.repository.VilleRepository;
import org.springframework.data.domain.Pageable;

@Service
public class VilleService {

	@Autowired
	private VilleRepository villeRepository;

	@Autowired
	private VilleMapper villeMapper;

	// Récupérer toutes les villes en tant que VilleDto
	public List<VilleDto> getAllVilles() {
		List<Ville> villes = villeRepository.findAll();
		return villes.stream().map(villeMapper::toDto) // Utilisation du mapper pour convertir chaque Ville en VilleDto
				.collect(Collectors.toList());
	}

	// Recherche des villes par nom avec VilleDto
	public List<VilleDto> rechercherVillesParNomPrefixe(String prefix) {
		List<Ville> villes = villeRepository.findByNomStartingWith(prefix);
		return villes.stream().map(villeMapper::toDto) // Conversion de chaque Ville en VilleDto
				.collect(Collectors.toList());
	}

	// Recherche des villes avec un nombre d'habitants supérieur à min avec VilleDto
	public List<VilleDto> rechercherVillesParNbHabitantsMin(int min) {
		List<Ville> villes = villeRepository.findByNbHabitantsGreaterThan(min);
		return villes.stream().map(villeMapper::toDto) // Conversion de chaque Ville en VilleDto
				.collect(Collectors.toList());
	}

	// Recherche des villes avec un nombre d'habitants entre min et max avec
	// VilleDto
	public List<VilleDto> rechercherVillesParNbHabitantsMinEtMax(int min, int max) {
		List<Ville> villes = villeRepository.findByNbHabitantsGreaterThanAndNbHabitantsLessThan(min, max);
		return villes.stream().map(villeMapper::toDto) // Conversion de chaque Ville en VilleDto
				.collect(Collectors.toList());
	}

	// Méthode pour trouver les villes par code de département et un nombre
	// d'habitants supérieur
	public List<VilleDto> findByDepartementCodeAndNbHabitantsGreaterThan(String code, int min) {
		List<Ville> villes = villeRepository.findByDepartementCodeAndNbHabitantsGreaterThan(code, min);
		return villes.stream().map(villeMapper::toDto) // Conversion de chaque Ville en VilleDto
				.collect(Collectors.toList());
	}

	// Méthode pour trouver les villes par code de département avec un nombre
	// d'habitants entre min et max
	public List<VilleDto> findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(String code, int min,
			int max) {
		List<Ville> villes = villeRepository.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(code,
				min, max);
		return villes.stream().map(villeMapper::toDto) // Conversion de chaque Ville en VilleDto
				.collect(Collectors.toList());
	}
}