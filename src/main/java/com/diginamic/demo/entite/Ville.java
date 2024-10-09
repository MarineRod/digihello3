package com.diginamic.demo.entite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Ville {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Le nom de la ville ne peut pas être nul et doit contenir au moins 2 caractères")
	@Size(min = 2, message = "Le nom de la ville doit contenir au moins 2 caractères")
	private String nom;

	@Min(value = 1, message = "Le nombre d'habitants doit être supérieur ou égal à 1")
	private int nbHabitants;

	public Ville() {
		super();
	}

	public Ville(int id, String nom, int nbHabitants) {
		this.id = id;
		this.nom = nom;
		this.nbHabitants = nbHabitants;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbHabitants() {
		return nbHabitants;
	}

	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

}
