package org.agaray.elecciones.repositories;

import org.agaray.elecciones.entities.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {

}
