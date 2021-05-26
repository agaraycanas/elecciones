package org.agaray.elecciones.controllers.rest;

import org.agaray.elecciones.entities.Candidatura;
import org.agaray.elecciones.repositories.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/auth")
public class AuthRestController {
	
	
	@Autowired
	CandidaturaRepository candidaturaRepository;
	
	@PatchMapping("candidatura/{idCandidatura}/{nVotos}")
	public Integer patchCandidatura(
			@PathVariable("idCandidatura") Long idCandidatura,
			@PathVariable("nVotos") int nVotos
			) {
		Candidatura candidatura = candidaturaRepository.getOne(idCandidatura);
		int votosFinales = candidatura.getNumVotos() + nVotos;
		candidatura.setNumVotos(votosFinales);
		candidaturaRepository.save(candidatura);
		return votosFinales;
	}
}
