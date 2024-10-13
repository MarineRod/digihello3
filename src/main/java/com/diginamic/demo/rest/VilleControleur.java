package com.diginamic.demo.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.service.VilleService;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
	@Autowired
    private VilleService villeService;

    // Récupérer toutes les villes en tant que VilleDto
    @GetMapping
    public List<VilleDto> getAllVilles() {
        return villeService.getAllVilles();
    }

    // Recherche de toutes les villes dont le nom commence par une chaîne de caractères donnée
    @GetMapping("/recherche-nom")
    public List<VilleDto> rechercherParNom(@RequestParam("prefix") String prefix) {
        return villeService.rechercherVillesParNomPrefixe(prefix);
    }

    // Recherche de toutes les villes dont le nombre d'habitants est supérieur à min
    @GetMapping("/recherche-nbHabitants")
    public List<VilleDto> rechercherParNbHabitants(@RequestParam("min") int min) {
        return villeService.rechercherVillesParNbHabitantsMin(min);
    }

    // Recherche de toutes les villes dont le nombre d'habitants est supérieur à min et inférieur à max
    @GetMapping("/recherche-nbHabitants-min-max")
    public List<VilleDto> rechercherParNbHabitantsMinEtMax(@RequestParam("min") int min, @RequestParam("max") int max) {
        return villeService.rechercherVillesParNbHabitantsMinEtMax(min, max);
    }

    // Recherche des villes par code de département et un nombre d'habitants supérieur
    @GetMapping("/departement/{code}/habitant/superieur/{min}")
    public ResponseEntity<List<VilleDto>> getVillesByDepartementCodeAndNbHabitantsGreaterThan(
            @PathVariable String code,
            @PathVariable int min) {
        List<VilleDto> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThan(code, min);
        return ResponseEntity.ok(villes);
    }

    // Recherche des villes par code de département et nombre d'habitants dans une plage donnée
    @GetMapping("/departement/{code}/habitant/plage")
    public ResponseEntity<List<VilleDto>> getVillesByDepartementCodeAndNbHabitantsRange(
            @PathVariable String code,
            @RequestParam int min,
            @RequestParam int max) {
        List<VilleDto> villes = villeService.findByDepartementCodeAndNbHabitantsGreaterThanAndNbHabitantsLessThan(code, min, max);
        return ResponseEntity.ok(villes);
    }
    

    // Méthode pour exporter les villes en CSV
    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportVillesToCSV(@RequestParam int minHabitants) throws IOException {
        List<VilleDto> villes = villeService.rechercherVillesParNbHabitantsMin(minHabitants);

        //crée un fichier CSV dans la mémoire à l'aide d'un ByteArrayOutputStream et d'un PrintWriter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        
        // Écriture des en-têtes CSV
        writer.println("Nom de la ville,Nombre d'habitants,Code département,Nom du département");

        // Initialisation du RestTemplate pour récupérer le nom du département
        RestTemplate restTemplate = new RestTemplate();

        // Parcourir les villes et écrire les données dans le CSV
        for (VilleDto ville : villes) {
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
