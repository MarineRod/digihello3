package com.diginamic.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diginamic.demo.entite.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    // Méthode pour rechercher un département par son nom (basée sur la méthode Dao)
    Optional<Departement> findByNom(String nom);

    // JpaRepository fournit déjà des méthodes comme save(), deleteById() et findAll(), 
    
}