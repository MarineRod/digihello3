package com.diginamic.demo.entite;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@Column(name = "nb_habitants")
	private int nbHabitants;

	@ManyToOne
	@JoinColumn(name = "departement_id")
	 @JsonBackReference // pour éviter la récursion infinie
	private Departement departement;

	public Ville() {
		super();
	}

	public Ville(int id,
			@NotBlank(message = "Le nom de la ville ne peut pas être nul et doit contenir au moins 2 caractères") @Size(min = 2, message = "Le nom de la ville doit contenir au moins 2 caractères") String nom,
			@Min(value = 1, message = "Le nombre d'habitants doit être supérieur ou égal à 1") int nbHabitants,
			Departement departement) {
		super();
		this.id = id;
		this.nom = nom;
		this.nbHabitants = nbHabitants;
		this.departement = departement;
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

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}



}
