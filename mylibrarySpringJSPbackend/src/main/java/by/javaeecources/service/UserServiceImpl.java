package by.javaeecources.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.javaeecources.model.User;
import by.javaeecources.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findByUsername(@NotBlank String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(@NotBlank String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Boolean existsByUsername(@NotBlank String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(@NotBlank String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Optional<User> findByUsernameOrEmail(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
