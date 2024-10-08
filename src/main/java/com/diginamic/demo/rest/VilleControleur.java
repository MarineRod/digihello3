package com.diginamic.demo.rest;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.diginamic.demo.service.Ville;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

	@GetMapping
	public List<Ville> getVilles() {
		List<Ville> villes = new ArrayList<>();

		
		villes.add(new Ville("Paris", 2140526));
		villes.add(new Ville("Lyon", 515695));
		villes.add(new Ville("Marseille", 861635));

		return villes;
	}
}
