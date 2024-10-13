package com.diginamic.demo.dto;

public class VilleDto {

	private String nom;
	private int nbHabitants;
	private String codeDepartement;
	private String nomDepartement;

	// Constructeurs, getters et setters
	public VilleDto() {
	}

	public VilleDto(String nom, int codeVille, int nbHabitants, String codeDepartement, String nomDepartement) {
		super();
		this.nom = nom;
		this.nbHabitants = nbHabitants;
		this.codeDepartement = codeDepartement;
		this.nomDepartement = nomDepartement;
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

	public String getCodeDepartement() {
		return codeDepartement;
	}

	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	public String getNomDepartement() {
		return nomDepartement;
	}

	public void setNomDepartement(String nomDepartement) {
		this.nomDepartement = nomDepartement;
	}

}
