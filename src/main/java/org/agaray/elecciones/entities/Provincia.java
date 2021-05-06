package org.agaray.elecciones.entities;

import javax.persistence.Column;

public class Provincia {

	@Column(unique = true)
	private String nombre;
	private int numEscanyos;
}
