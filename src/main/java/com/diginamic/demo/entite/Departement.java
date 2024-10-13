package com.diginamic.demo.entite;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Departement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Le nom du département ne peut pas être nul")
	private String nom;

	private String code;

	@OneToMany(mappedBy = "departement")

	private List<Ville> villes = new ArrayList<>();

	public Departement() {
		super();
	}

	public Departement(int id, @NotBlank(message = "Le nom du département ne peut pas être nul") String nom,
			String code, List<Ville> villes) {
		super();
		this.id = id;
		this.nom = nom;
		this.code = code;
		this.villes = villes;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Ville> getVilles() {
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}

	// Méthode d'ajout d'une ville pour gérer la relation bidirectionnelle
	public void addVille(Ville ville) {
		villes.add(ville);
		ville.setDepartement(this);
	}

	// Méthode de suppression d'une ville
	public void removeVille(Ville ville) {
		villes.remove(ville);
		ville.setDepartement(null);
	}
}
