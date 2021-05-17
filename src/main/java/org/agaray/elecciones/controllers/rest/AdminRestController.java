package org.agaray.elecciones.controllers.rest;

import java.util.List;

import org.agaray.elecciones.entities.Candidatura;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/admin")
public class AdminRestController {
	public List<Candidatura> getCandidaturas() {
		return null;
	}
}
