package com.diginamic.demo.mapper;

import org.springframework.stereotype.Component;

import com.diginamic.demo.dto.DepartementDto;
import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;

@Component
public class DepartementMapper {

    // Conversion de l'entité Departement en DepartementDto
    public DepartementDto toDto(Departement departement) {
        DepartementDto dto = new DepartementDto();
        dto.setCodeDepartement(departement.getCode());  // Correspondance avec le code du département
        dto.setNomDepartement(departement.getNom());    // Correspondance avec le nom du département

        // Agrégation du nombre d'habitants à partir des villes du département
        int totalHabitants = departement.getVilles().stream()
                                         .mapToInt(Ville::getNbHabitants)
                                         .sum();
        dto.setNbHabitants(totalHabitants); // Remplissage de l'attribut nbHabitants dans le DTO
        return dto;
    }

    // Conversion de DepartementDto en entité Departement
    public Departement toEntity(DepartementDto dto) {
        Departement departement = new Departement();
        departement.setCode(dto.getCodeDepartement());  // Mapping du code
        departement.setNom(dto.getNomDepartement());    // Mapping du nom
        // Note : Pas de mapping direct pour le nombre d'habitants, car il est dérivé.
        return departement;
    }
}
