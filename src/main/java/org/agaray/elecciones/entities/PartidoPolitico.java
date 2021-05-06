package org.agaray.elecciones.entities;

import javax.persistence.Column;

public class PartidoPolitico {
	@Column(unique = true)
	private String siglas;

	@Column(unique = true)
	private String nombre;
}
