package org.agaray.elecciones.controllers.rest;

import java.util.List;

import org.agaray.elecciones.entities.Candidatura;
import org.agaray.elecciones.repositories.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/admin")
public class AdminRestController {
	
	@Autowired
	private CandidaturaRepository candidaturaRepository;
	
	@GetMapping("candidaturas/{idEleccion}")
	public List<Candidatura> getCandidaturas(
			@PathVariable("idEleccion") Long idEleccion
			) {
		return candidaturaRepository.findAllByEleccionId(idEleccion);
	}
}
