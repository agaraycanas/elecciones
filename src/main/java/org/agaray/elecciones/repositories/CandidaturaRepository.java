package org.agaray.elecciones.repositories;

import java.util.List;

import org.agaray.elecciones.entities.Candidatura;
import org.agaray.elecciones.entities.Eleccion;
import org.agaray.elecciones.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
	public List<Candidatura> findAllByEleccionId(Long idEleccion);
	public List<Candidatura> findAllByProvinciaId(Long idEleccion);
	public List<Candidatura> findAllByProvinciaAndByEleccion(Provincia p,Eleccion e);
}
