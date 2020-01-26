package by.javaeecources.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import by.javaeecources.model.Role;
import by.javaeecources.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);
}
