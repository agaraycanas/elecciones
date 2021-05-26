package org.agaray.elecciones.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.agaray.elecciones.entities.Candidatura;
import org.agaray.elecciones.entities.Eleccion;
import org.agaray.elecciones.entities.Provincia;
import org.agaray.elecciones.repositories.CandidaturaRepository;
import org.agaray.elecciones.repositories.EleccionRepository;
import org.agaray.elecciones.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private CandidaturaRepository candidaturaRepository;
	
	@Autowired
	EleccionRepository eleccionRepository;

	@Autowired
	ProvinciaRepository provinciaRepository;

	@PostMapping("seleccionarProvinciaYEleccion")
	public String seleccionarProvinciaYEleccion(
			@RequestParam("idEleccion") Long idEleccion,
			@RequestParam("idProvincia") Long idProvincia,
			HttpSession s
			) {
		Eleccion e = eleccionRepository.getOne(idEleccion);
		Provincia p = provinciaRepository.getOne(idProvincia);
		System.err.println("ALMACENO "+e); //TODO Por qué pasa esto?
		System.err.println("ALMACENO "+p); //TODO Por qué pasa esto?
		s.setAttribute("eleccion", e);
		s.setAttribute("provincia", p);
		
		return "redirect:/";
		
	}

	@GetMapping("candidatura/r")
	public String candidaturaR(
			ModelMap m,
			HttpSession s) {
		Eleccion e = (Eleccion)(s.getAttribute("eleccion"));
		Provincia p = (Provincia)(s.getAttribute("provincia"));
		List<Candidatura> candidaturas = candidaturaRepository.findAllByProvinciaIdAndEleccionId(p.getId(), e.getId()); 
		
		m.put("eleccion", e);
		m.put("provincia", p);
		m.put("candidaturas", candidaturas);
		m.put("view","auth/candidatura/r");
		return "_t/frame";
	}
}
