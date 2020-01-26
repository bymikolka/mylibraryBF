package by.javaeecources.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;

import by.javaeecources.model.User;

@Service
public interface UserService {
	public Optional<User> findById(Long id);

	Optional<User> findByUsername(@NotBlank String username);

	Optional<User> findByEmail(@NotBlank String email);

	Boolean existsByUsername(@NotBlank String username);

	Boolean existsByEmail(@NotBlank String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	List<User> findAll();

}
