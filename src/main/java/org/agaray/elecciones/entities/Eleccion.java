package org.agaray.elecciones.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Eleccion {
	
	// =================================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate fecha;
	
	@OneToMany(mappedBy = "eleccion")
	private Collection<Candidatura> candidaturas;

	// =================================================

	public Eleccion() {
		this.candidaturas = new HashSet<>();
	}

	public Eleccion(LocalDate fecha) {
		super();
		this.fecha = fecha;
		this.candidaturas = new HashSet<>();
	}

	// =================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Collection<Candidatura> getCandidaturas() {
		return candidaturas;
	}

	public void setCandidaturas(Collection<Candidatura> candidaturas) {
		this.candidaturas = candidaturas;
	}
	

	
	
}
