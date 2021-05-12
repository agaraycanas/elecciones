package org.agaray.elecciones.repositories;

import org.agaray.elecciones.entities.ComunidadAutonoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunidadAutonomaRepository extends JpaRepository<ComunidadAutonoma, Long> {

}
