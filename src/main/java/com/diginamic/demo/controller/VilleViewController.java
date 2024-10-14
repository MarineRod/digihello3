package com.diginamic.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.diginamic.demo.service.DepartementService;
import com.diginamic.demo.service.VilleService;

@Controller
public class VilleViewController {
    
    @Autowired
    private VilleService villeService;
    
    @Autowired
    private DepartementService departementService;
    
    @GetMapping("/town")
    public String afficherListeVilles(Model model) {
        model.addAttribute("villes", villeService.getAllVilles());
        model.addAttribute("departements", departementService.getAllDepartements());
        return "villes";  
    }
}
	
	
	
	
//	
//        // Retourner le nom de la vue (villes.html)
//        return "villes";
//   

