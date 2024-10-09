package com.diginamic.demo.dao;

import java.util.List;
import java.util.Optional;

import com.diginamic.demo.entite.Departement;

public interface DepartementDao {

	List<Departement> findAll();

	Departement findById(int id);

	Optional<Departement> findByNom(String nom);

	Departement save(Departement departement);
	
	
	Departement update(Departement departement);

	void delete(int id);

}
