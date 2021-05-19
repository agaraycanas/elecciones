package org.agaray.elecciones.entities;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PartidoPolitico {

	// ==================================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String siglas;

	@Column(unique = true)
	private String nombre;

	@OneToMany(mappedBy = "partidoPolitico")
	@JsonIgnore
	private Collection<Candidatura> candidaturas;

	// ==================================================
	public PartidoPolitico() {
		this.candidaturas = new HashSet<>();
	}

	public PartidoPolitico(String siglas, String nombre) {
		super();
		this.siglas = siglas;
		this.nombre = nombre;
		this.candidaturas = new HashSet<>();
	}

	// =================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Candidatura> getCandidaturas() {
		return candidaturas;
	}

	public void setCandidaturas(Collection<Candidatura> candidaturas) {
		this.candidaturas = candidaturas;
	}

	// ==================================================

	
	
}
