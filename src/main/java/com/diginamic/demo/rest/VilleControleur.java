package com.diginamic.demo.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.exception.CustomValidationException;
import com.diginamic.demo.exception.VilleNotFoundException;
import com.diginamic.demo.mapper.VilleMapper;
import com.diginamic.demo.service.VilleService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

	@Autowired
	private VilleService villeService;

	@Autowired
	private VilleMapper villeMapper;
	
	// Récupérer toutes les villes en tant que VilleDto
	@GetMapping
	public ResponseEntity<List<VilleDto>> getAllVilles() {
		List<Ville> villes = villeService.getAllVilles();
		List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(villeDtos);
	}

	// Recherche de toutes les villes dont le nom commence par une chaine de
	// caractères
	// données
	@GetMapping("/recherche")
	public ResponseEntity<List<VilleDto>> rechercherVillesParNomPrefixe(@RequestParam String prefix)
			throws VilleNotFoundException {
		List<Ville> villes = villeService.rechercherVillesParNomPrefixe(prefix);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville dont le nom commence par '" + prefix + "' n'a été trouvée.");
		}
		List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(villeDtos);
	}

	// Recherche des villes avec un nombre d'habitants supérieur à min avec VilleDto
	@GetMapping("/habitants")
	public ResponseEntity<List<VilleDto>> rechercherVillesParNbHabitantsMin(@RequestParam int min)
			throws VilleNotFoundException {
		List<Ville> villes = villeService.rechercherVillesParNbHabitantsMin(min);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n'a une population supérieure à " + min + ".");
		}
		List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(villeDtos);
	}

	// Recherche des villes avec un nombre d'habitants entre min et max avec
	// VilleDto
	 @GetMapping("/habitants/min-max")
	    public ResponseEntity<List<VilleDto>> rechercherVillesParNbHabitantsMinEtMax(@RequestParam int min,
	                                                                                   @RequestParam int max) throws VilleNotFoundException {
	        List<Ville> villes = villeService.rechercherVillesParNbHabitantsMinEtMax(min, max);
	        if (villes.isEmpty()) {
	            throw new VilleNotFoundException("Aucune ville n'a une population comprise entre " + min + " et " + max + ".");
	        }
	        List<VilleDto> villeDtos = villes.stream().map(villeMapper::toDto).collect(Collectors.toList());
	        return ResponseEntity.ok(villeDtos);
	    }

	// Créer une nouvelle ville
	 @PostMapping
	 public ResponseEntity<VilleDto> createVille(@RequestBody VilleDto villeDto) {
	     Ville ville = villeMapper.toEntity(villeDto);
	     try {
	         Ville savedVille = villeService.createVille(ville);
	         VilleDto savedVilleDto = villeMapper.toDto(savedVille);
	         return ResponseEntity.status(HttpStatus.CREATED).body(savedVilleDto);
	     } catch (CustomValidationException e) {
	         return ResponseEntity.badRequest().body(new VilleDto());
	     }
	 }

	// Modifier une nouvelle ville
	 @PutMapping("/{id}")
	 public ResponseEntity<VilleDto> updateVille(@PathVariable int id, @RequestBody VilleDto villeDto) {
	     Ville ville = villeMapper.toEntity(villeDto);
	     ville.setId(id); // Assigner l'ID à la ville à mettre à jour
	     try {
	         Ville updatedVille = villeService.updateVille(ville);
	         VilleDto updatedVilleDto = villeMapper.toDto(updatedVille);
	         return ResponseEntity.ok(updatedVilleDto);
	     } catch (CustomValidationException e) {
	         return ResponseEntity.badRequest().body(new VilleDto());
	     }
	 }

	// Supprimer une ville
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVille(@PathVariable int id) {
		villeService.deleteVille(id); // Appel à la méthode de service pour supprimer la ville
		return ResponseEntity.noContent().build(); // Renvoie un statut 204 No Content
	}

	// Recherche des villes d’un département dont la population est supérieure à min
	@GetMapping("/departement/{codeDept}/habitants")
	public ResponseEntity<List<Ville>> rechercherVillesParDepartementEtNbHabitantsMin(@PathVariable String codeDept,
			@RequestParam int min) throws VilleNotFoundException {
		List<Ville> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThan(codeDept, min);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException(
					"Aucune ville n’a une population supérieure à " + min + " dans le département " + codeDept + ".");
		}
		return ResponseEntity.ok(villes);
	}

	// Recherche des villes d’un département dont la population est supérieure à min
	// et inférieure à max
	@GetMapping("/departement/{codeDept}/habitants/min-max")
	public ResponseEntity<List<Ville>> rechercherVillesParDepartementEtNbHabitantsMinEtMax(
			@PathVariable String codeDept, @RequestParam int min, @RequestParam int max) throws VilleNotFoundException {
		List<Ville> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(codeDept,
				min, max);
		if (villes.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + min + " et " + max
					+ " dans le département " + codeDept + ".");
		}
		return ResponseEntity.ok(villes);
	}

    // Méthode pour exporter les villes en CSV
    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportVillesToCSV(@RequestParam int minHabitants) throws IOException {
        List<Ville> villes = villeService.rechercherVillesParNbHabitantsMin(minHabitants);
        
        List<VilleDto> villeDtos = villes.stream()
                .map(villeMapper::toDto)
                .collect(Collectors.toList());

        //crée un fichier CSV dans la mémoire à l'aide d'un ByteArrayOutputStream et d'un PrintWriter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        
        // Écriture des en-têtes CSV
        writer.println("Nom de la ville,Nombre d'habitants,Code département,Nom du département");

        // Initialisation du RestTemplate pour récupérer le nom du département
        RestTemplate restTemplate = new RestTemplate();

        // Parcourir les villes et écrire les données dans le CSV
        for (VilleDto ville : villeDtos) {
            String codeDepartement = ville.getCodeDepartement();
            String nomDepartement = getNomDepartement(restTemplate, codeDepartement);
            writer.printf("%s,%d,%s,%s%n", ville.getNom(), ville.getNbHabitants(), codeDepartement, nomDepartement);
        }

        writer.flush();//Cette méthode s'assure que toutes les données encore présentes 
        //dans le PrintWriter sont bien envoyées dans le ByteArrayOutputStream.
        byte[] csvBytes = outputStream.toByteArray(); //Cette ligne convertit tout le contenu accumulé 
        //dans le ByteArrayOutputStream en un tableau d’octets (byte[]).

     // Définir les en-têtes de réponse pour indiquer que c'est un fichier CSV
        HttpHeaders headers = new HttpHeaders();
        //HttpHeaders est une classe fournie par Spring pour gérer les en-têtes HTTP dans une requête ou une réponse.
        //Ici, on crée un nouvel objet HttpHeaders pour définir les en-têtes spécifiques qui indiqueront au client (navigateur ou autre) que la réponse contient un fichier téléchargeable.
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=villes.csv");
        //"attachment; filename=villes.csv" indique que le contenu doit être traité comme un fichier à télécharger, et le nom de ce fichier sera villes.csv.
       //"attachment" force le téléchargement,
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        //csvBytes -> fichier CSV converti en tableau d'octets
        //headers sont les en-têtes définis précédemment (pour indiquer que le fichier est téléchargeable et au format CSV)
    }
    
    // Méthode pour récupérer le nom du département depuis l'API externe
    private String getNomDepartement(RestTemplate restTemplate, String codeDepartement) {
    	// //getNomDepartement construit une URL pour accéder à l'API gouvernementale en fonction du code de département fourni.
        String url = String.format("https://geo.api.gouv.fr/departements/%s?fields=nom", codeDepartement);      
        try {
        	//envoie une requête GET à cette URL pour obtenir les données du département
            DepartementResponse response = restTemplate.getForObject(url, DepartementResponse.class);
            //traite la réponse pour en extraire le nom du département, ou retourne "Inconnu" en cas de problème.
            return response != null ? response.getNom() : "Inconnu";
        } catch (Exception e) {
            return "Inconnu"; // Gestion d'erreur en cas d'échec de l'appel à l'API
        }
    }

    // Classe interne pour modéliser la réponse de l'API externe
    private static class DepartementResponse {
        private String nom;

        public String getNom() {
            return nom;
        }

    }
	
	
}
