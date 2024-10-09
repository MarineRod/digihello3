package com.diginamic.demo.dao;

import java.util.List;

import com.diginamic.demo.entite.Ville;

public interface VilleDao {
	
	List<Ville> findAll();

	Ville findById(int id);

	Ville save(Ville ville);

	void delete(int id);
}