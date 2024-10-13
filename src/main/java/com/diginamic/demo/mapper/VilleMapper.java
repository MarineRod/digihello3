package com.diginamic.demo.mapper;

import org.springframework.stereotype.Component;

import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;

@Component
public class VilleMapper {

    public VilleDto toDto(Ville ville) {
    	 VilleDto dto = new VilleDto();
         dto.setId(ville.getId()); 
         dto.setNom(ville.getNom()); //L'id de Ville devient codeVille dans VilleDto
         dto.setNbHabitants(ville.getNbHabitants());
         dto.setCodeDepartement(ville.getDepartement().getCode());  // Utilisation du code du département
         dto.setNomDepartement(ville.getDepartement().getNom());  // Utilisation du nom du département
         return dto;
    }

    public Ville toEntity(VilleDto villeDto) {
        if (villeDto == null) {
            return null;
        }

        Ville ville = new Ville();
        ville.setId(villeDto.getId()); // Assuming the codeVille is numeric
        ville.setNbHabitants(villeDto.getNbHabitants());
        
        Departement departement = new Departement();
        departement.setCode(villeDto.getCodeDepartement());
        departement.setNom(villeDto.getNomDepartement());
        
        ville.setDepartement(departement);

        return ville;
    }
}

