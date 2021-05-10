package org.agaray.elecciones.repositories;

import org.agaray.elecciones.entities.Eleccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EleccionRepository extends JpaRepository<Eleccion, Long> {

}
