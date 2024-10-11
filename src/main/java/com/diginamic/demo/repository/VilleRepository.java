package com.diginamic.demo.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.diginamic.demo.entite.Ville;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

	List<Ville> findByNomStartingWith(String prefix);

	List<Ville> findByNbHabitantsGreaterThan(int min);

	List<Ville> findByNbHabitantsGreaterThanAndNbHabitantsLessThan(int min, int max);

	List<Ville> findByDepartementCodeAndNbHabitantsGreaterThan(String code, int min);

	List<Ville> findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(String code, int min, int max);

	Page<Ville> findTopNByDepartementCodeOrderByNbHabitantsDesc(String code,
			Pageable pageable);

  
   

}