package org.agaray.elecciones.entities;

import javax.persistence.Column;

public class ComunidadAutonoma {
	@Column(unique = true)
	private String nombre;
}
