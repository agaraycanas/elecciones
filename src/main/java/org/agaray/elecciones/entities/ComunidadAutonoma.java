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
public class ComunidadAutonoma {

	// =================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String nombre;

	@OneToMany(mappedBy = "comunidadAutonoma")
	@JsonIgnore
	private Collection<Provincia> provincias;
	
	// =================================================

	public ComunidadAutonoma() {
		this.provincias = new HashSet<>();
	}

	public ComunidadAutonoma(String nombre) {
		super();
		this.nombre = nombre;
		this.provincias = new HashSet<>();
	}

	// =================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(Collection<Provincia> provincias) {
		this.provincias = provincias;
	}
	

	
	
	
	
}
