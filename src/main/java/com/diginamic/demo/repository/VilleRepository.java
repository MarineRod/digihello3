package com.diginamic.demo.repository;

import java.awt.print.Pageable;
import java.util.List;

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

//	List<Ville> findTopNByDepartementCodeOrderByNbHabitantsDesc(String code,
//			Pageable pageable);

//    List<Ville> findByDepartementIdAndNbHabitantsGreaterThan(int departementId, int min);
//    
//   
//    List<Ville> findByDepartementIdAndNbHabitantsGreaterThanAndNbHabitantsLessThan(int departementId, int min, int max);
//    
//   
//    List<Ville> findTopNByDepartementIdOrderByNbHabitantsDesc(int departementId, org.springframework.data.domain.Pageable pageable);

}