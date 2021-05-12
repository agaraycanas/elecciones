package org.agaray.elecciones.controllers;

import java.time.LocalDate;

import org.agaray.elecciones.entities.Eleccion;
import org.agaray.elecciones.repositories.EleccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
	
	@Autowired
	private EleccionRepository eleccionRepository;

	@GetMapping("/eleccion/c")
	public String cGet(
			ModelMap m) {
		m.put("view", "admin/eleccion/c");
		return "/_t/frame";
	}

	
	@PostMapping("/eleccion/c")
	public String cPost(
			@RequestParam("fecha")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate fecha
			) {
		eleccionRepository.save( new Eleccion(fecha) );
		return "redirect:/eleccion/r";
	}
	
	@GetMapping("/eleccion/r")
	public String rGet(
			ModelMap m
			) {
		m.put("elecciones", eleccionRepository.findAll());
		m.put("view", "anon/eleccion/r");
		return "/_t/frame";
	}

}
