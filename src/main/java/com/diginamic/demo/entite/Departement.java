package com.diginamic.demo.entite;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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

    @NotBlank(message = "Le nom du département ne peut pas être nul.")
    private String nom;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // pour gérer la sérialisation
    private List<Ville> villes = new ArrayList<>();

    // Constructeurs, getters et setters

    public Departement() {
    }

    public Departement(int id, String nom) {
        this.id = id;
        this.nom = nom;
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
