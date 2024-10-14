package com.diginamic.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.diginamic.demo.service.VilleService;

@Controller
public class IndexController {
	
	@GetMapping
    public String index() {
        // Retourne le nom de la vue (sans extension)
        return "index"; // Affiche la page index.html
    }
}
