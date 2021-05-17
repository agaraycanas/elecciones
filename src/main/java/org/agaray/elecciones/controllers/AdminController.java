package org.agaray.elecciones.controllers;

import java.time.LocalDate;

import org.agaray.elecciones.entities.Candidatura;
import org.agaray.elecciones.entities.Eleccion;
import org.agaray.elecciones.entities.PartidoPolitico;
import org.agaray.elecciones.entities.Provincia;
import org.agaray.elecciones.repositories.CandidaturaRepository;
import org.agaray.elecciones.repositories.EleccionRepository;
import org.agaray.elecciones.repositories.PartidoPoliticoRepository;
import org.agaray.elecciones.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private EleccionRepository eleccionRepository;
	
	@Autowired
	private PartidoPoliticoRepository partidoPoliticoRepository;

	@Autowired
	private CandidaturaRepository candidaturaRepository;

	@Autowired
	private ProvinciaRepository provinciaRepository;

	
	// ======================================
	// ELECCIÓN
	// ======================================

	@GetMapping("eleccion/c")
	public String eleccionCGet(
			ModelMap m) {
		m.put("view", "admin/eleccion/c");
		return "/_t/frame";
	}

	
	@PostMapping("eleccion/c")
	public String eleccionCPost(
			@RequestParam("fecha")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate fecha
			) {
		eleccionRepository.save( new Eleccion(fecha) );
		return "redirect:/admin/eleccion/r";
	}
	
	@GetMapping("eleccion/r")
	public String eleccionRGet(
			ModelMap m
			) {
		m.put("elecciones", eleccionRepository.findAll());
		m.put("view", "admin/eleccion/r");
		return "/_t/frame";
	}

	// ======================================
	// PARTIDO POLÍTICO
	// ======================================
	@GetMapping("pp/c")
	public String partidoPoliticoCGet(
			ModelMap m) {
		m.put("view", "admin/pp/c");
		return "/_t/frame";
	}

	
	@PostMapping("pp/c")
	public String partidoPoliticoCPost(
			@RequestParam("siglas") String siglas,
			@RequestParam("nombre") String nombre
			) {
		partidoPoliticoRepository.save( new PartidoPolitico(siglas,nombre) );
		return "redirect:/admin/pp/r";
	}
	
	@GetMapping("pp/r")
	public String partidoPoliticoRGet(
			ModelMap m
			) {
		m.put("partidos", partidoPoliticoRepository.findAll());
		m.put("view", "admin/pp/r");
		return "/_t/frame";
	}
	

	// ======================================
	// CANDIDATURA
	// ======================================
	@GetMapping("candidatura/c")
	public String candidaturaCGet(
			ModelMap m) {
		m.put("elecciones",eleccionRepository.findAll());
		m.put("pps",partidoPoliticoRepository.findAll());
		m.put("provincias",provinciaRepository.findAll());
		
		m.put("view", "admin/candidatura/c");
		return "/_t/frame";
	}

	
	@PostMapping("candidatura/c")
	public String candidaturaCPost(
			@RequestParam("idEleccion") Long idEleccion,
			@RequestParam("idProvincia") Long idProvincia,
			@RequestParam("idPartidoPolitico") Long idPartidoPolitico
			) {
		
		Eleccion eleccion = eleccionRepository.getOne(idEleccion);
		Provincia provincia = provinciaRepository.getOne(idProvincia);
		PartidoPolitico partidoPolitico = partidoPoliticoRepository.getOne(idPartidoPolitico);
		
		candidaturaRepository.save( new Candidatura(eleccion, provincia, partidoPolitico) );
		return "redirect:/admin/candidatura/r";
	}
	
	@GetMapping("candidatura/r")
	public String candidaturaRGet(
			ModelMap m
			) {
		m.put("elecciones", eleccionRepository.findAll());
		m.put("view", "admin/candidatura/r");
		return "/_t/frame";
	}


}
