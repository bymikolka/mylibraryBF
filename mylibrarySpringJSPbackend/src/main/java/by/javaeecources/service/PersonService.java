package by.javaeecources.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import by.javaeecources.model.Person;
@Service
public interface PersonService {

	
	public void deletePersonById(Long id);
	
	public Optional<Person> findById(Long id);
	
	public Person findPersonById(Long id);
	
	public List<Person> findAll();
	
	public List<Person> findAll(Integer pageNo, Integer pageSize, String sortBy);
	
	public List<Person> findByFirstname(String name);
	
	public Person createOrUpdatePerson(Person person);
	
	List<Person> findByFirstnameOrLastnameOrderById(String firstName, String lastName);
	
	public Long count();
}
