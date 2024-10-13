package com.diginamic.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

	List<Ville> findByNomStartingWith(String prefix);

	List<Ville> findByNbHabitantsGreaterThan(int min);

	List<Ville> findByNbHabitantsGreaterThanAndNbHabitantsLessThan(int min, int max);

	List<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String code, int min);

	List<Ville> findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(String code, int min, int max);

	//Optional<Ville> findByNomAndCodeDepartement(String nom, String code);

	List<Ville> findTopNByDepartementCodeOrderByNbHabitantsDesc(String code, Pageable pageable);

	List<Ville> findByDepartementCode(String code, Pageable pageable);

	

}