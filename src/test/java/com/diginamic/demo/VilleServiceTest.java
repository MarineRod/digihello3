package com.diginamic.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

		List<Ville> result = villeService.rechercherVillesParNomPrefixe("Par");

		assertThat(result).hasSize(2); // Paris et Parthenay
		assertThat(result).extracting(Ville::getNom).contains("Paris", "Parthenay");

	}

}
