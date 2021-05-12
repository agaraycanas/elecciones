package org.agaray.elecciones.repositories;

import org.agaray.elecciones.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User getByLoginname(String loginname);
}
