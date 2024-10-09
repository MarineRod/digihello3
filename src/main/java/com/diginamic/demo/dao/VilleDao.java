package com.diginamic.demo.dao;

import java.util.List;
import java.util.Optional;

import com.diginamic.demo.entite.Ville;

public interface VilleDao {

	List<Ville> findAll();

	Ville findById(int id);

	Optional<Ville> findByNom(String nom);

	Ville save(Ville ville);
	
	Ville update(Ville ville);

	void delete(int id);

	List<Ville> findTopNLargestCitiesByDepartment(int departmentId, int n);

	List<Ville> findCitiesByPopulationRangeAndDepartment(int minPopulation, int maxPopulation, int departmentId);
}