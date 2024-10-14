package com.diginamic.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.repository.VilleRepository;
import com.diginamic.demo.service.VilleService;

@SpringBootTest
@ActiveProfiles("test")
class VilleServiceTest {

	@Autowired
	private VilleService villeService;

	@MockBean
	private VilleRepository villeRepository;

	@Test
	public void testRechercheVillesParNomPrefixe() {
		// Créer des objets Ville pour le test
		Ville paris = new Ville();
		paris.setNom("Paris");

		Ville parthenay = new Ville();
		parthenay.setNom("Parthenay");

		Ville lyon = new Ville();
		lyon.setNom("Lyon");

		Mockito.when(villeRepository.findByNomStartingWith("Par")).thenReturn(List.of(paris, parthenay));

		List<Ville> result = villeService.rechercherVillesParNomPrefixe("Par");

		assertThat(result).hasSize(2); // Paris et Parthenay
		//assertThat(result).extracting(Ville::getNom).contains("Paris", "Parthenay");

	}

	@Test
	public void testRechercherVillesParNomPrefixe_AucuneCorrespondance() {
		
		Ville paris = new Ville();
		paris.setNom("Paris");

		Ville parthenay = new Ville();
		parthenay.setNom("Parthenay");

		Ville lyon = new Ville();
		lyon.setNom("Lyon");

		Mockito.when(villeRepository.findByNomStartingWith("Xeu")).thenReturn(Collections.emptyList());
		
		List<Ville> result = villeService.rechercherVillesParNomPrefixe("Xeu");

		// Vérifier que la liste est vide
		assertThat(result).hasSize(0);
	}

}
