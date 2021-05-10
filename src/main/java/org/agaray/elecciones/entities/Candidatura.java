package org.agaray.elecciones.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Candidatura {
	

	// =================================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int numVotos;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Eleccion eleccion;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Provincia provincia;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private PartidoPolitico partidoPolitico;
	
	// =================================================

	public Candidatura() {
		
	}

	public Candidatura(int numVotos, Eleccion eleccion, Provincia provincia, PartidoPolitico partidoPolitico) {
		super();
		this.numVotos = numVotos;
		this.eleccion = eleccion;
		this.provincia = provincia;
		this.partidoPolitico = partidoPolitico;
	}

	// =================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumVotos() {
		return numVotos;
	}

	public void setNumVotos(int numVotos) {
		this.numVotos = numVotos;
	}

	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public PartidoPolitico getPartidoPolitico() {
		return partidoPolitico;
	}

	public void setPartidoPolitico(PartidoPolitico partidoPolitico) {
		this.partidoPolitico = partidoPolitico;
	}
	

	
	
}
