package com.diginamic.demo.rest;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.diginamic.demo.dto.DepartementDto;
import com.diginamic.demo.dto.VilleDto;
import com.diginamic.demo.entite.Departement;
import com.diginamic.demo.entite.Ville;
import com.diginamic.demo.exception.CustomValidationException;
import com.diginamic.demo.mapper.DepartementMapper;
import com.diginamic.demo.mapper.VilleMapper;
import com.diginamic.demo.service.DepartementService;
import com.diginamic.demo.service.VilleService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

	@Autowired
	private DepartementService departementService;

	@Autowired
	private VilleService villeService;

	@Autowired
	private DepartementMapper departementMapper;

	@Autowired
	private VilleMapper villeMapper;

	@Autowired
	private RestTemplate restTemplate;

	// Récupérer tous les départements en tant que DepartementDto
	@GetMapping
	public ResponseEntity<List<DepartementDto>> getAllDepartements() {
		List<Departement> departements = departementService.getAllDepartements();
		List<DepartementDto> departementDtos = departements.stream().map(departementMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(departementDtos);
	}

	// Récupérer un département par ID
	@GetMapping("/{id}")
	public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) {
		Departement departement = departementService.getDepartementById(id);
		return ResponseEntity.ok(departementMapper.toDto(departement));
	}

	// Recherche d'un département par nom
	@GetMapping("/nom/{nom}")
	public ResponseEntity<DepartementDto> getDepartementByNom(@PathVariable String nom) {
		Optional<Departement> departement = departementService.getDepartementByNom(nom);
		return departement.map(d -> ResponseEntity.ok(departementMapper.toDto(d)))
				.orElse(ResponseEntity.notFound().build());
	}

	// Création d'un nouveau département
	@PostMapping
	public ResponseEntity<DepartementDto> createDepartement(@RequestBody DepartementDto departementDto)
			throws CustomValidationException {
		// Conversion de DepartementDto à Departement
		Departement departement = departementMapper.toEntity(departementDto);

		// Sauvegarde du département
		Departement savedDepartement = departementService.saveDepartement(departement);
		// Retourne le département sauvegardé en DTO avec un statut 201 (CREATED)
		return ResponseEntity.status(HttpStatus.CREATED).body(departementMapper.toDto(savedDepartement));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DepartementDto> updateDepartement(@PathVariable int id,
			@RequestBody DepartementDto departementDto) {
		try {
			Departement departement = departementMapper.toEntity(departementDto);
			Departement updatedDepartement = departementService.updateDepartement(id, departement);
			DepartementDto updatedDepartementDto = departementMapper.toDto(updatedDepartement);
			return ResponseEntity.ok(updatedDepartementDto);
		} catch (CustomValidationException e) {
			return ResponseEntity.badRequest().body(new DepartementDto());
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Suppression d'un département
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
		departementService.deleteDepartement(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/export/pdf/{codeDepartement}")
	public ResponseEntity<byte[]> exportPdf(@PathVariable String codeDepartement,
	        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
	    // Récupérer le nom du département
	    String nomDepartement = getNomDepartement(restTemplate, codeDepartement);

	    // Récupérer la liste des villes du département avec pagination
	    Pageable pageable = PageRequest.of(page, size);
	    List<Ville> villes = villeService.getVillesByDepartementCode(codeDepartement, pageable);

	    // Vérifiez si la liste des villes est vide
	    if (villes.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // Retourne 204 si aucune ville
	    }

	    List<VilleDto> villeDtos = villes.stream()
	            .map(villeMapper::toDto)
	            .collect(Collectors.toList());

	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    try {
	        // Créer un document PDF
	        Document document = new Document();
	        PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();

	        // Titre du PDF
	        document.add(new Paragraph("Nom du département : " + nomDepartement));
	        document.add(new Paragraph("Code département : " + codeDepartement));
	        document.add(new Paragraph(" ")); // Saut de ligne

	        // Ajouter un tableau pour afficher les villes
	        PdfPTable table = new PdfPTable(2); // 2 colonnes : Nom de la ville, Population
	        table.addCell("Nom de la ville");
	        table.addCell("Population");

	        // Remplir le tableau avec les données des villes
	        for (VilleDto ville : villeDtos) {
	            table.addCell(ville.getNom());
	            table.addCell(String.valueOf(ville.getNbHabitants()));
	        }

	        
	        
	        // Ajouter le tableau au document
	        document.add(table);

	        // Fermer le document PDF
	        document.close();

	    } catch (DocumentException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }

	    // Créer les en-têtes de la réponse HTTP pour le téléchargement du PDF
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDispositionFormData("filename", "villes.pdf");

	    // Retourner le PDF sous forme de tableau de bytes
	    return ResponseEntity.ok().headers(headers).body(byteArrayOutputStream.toByteArray());
	}

	// Méthode pour récupérer le nom du département depuis l'API externe
	private String getNomDepartement(RestTemplate restTemplate, String codeDepartement) {
		String url = String.format("https://geo.api.gouv.fr/departements/%s?fields=nom", codeDepartement);

		// Effectuer la requête GET avec RestTemplate pour obtenir les informations du
		// département
		ResponseEntity<Map<String, String>> response = restTemplate.getForEntity(url,
				(Class<Map<String, String>>) (Class<?>) Map.class);

		// Vérifier si la requête a réussi et extraire le nom du département
		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			return response.getBody().get("nom"); // Retourner le nom du département
		} else {
			// En cas d'erreur, lancer une exception
			throw new RuntimeException(
					"Impossible de récupérer le nom du département pour le code : " + codeDepartement);
		}
	}
}