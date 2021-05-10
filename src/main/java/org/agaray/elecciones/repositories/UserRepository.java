package org.agaray.elecciones.repositories;

import org.agaray.elecciones.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
