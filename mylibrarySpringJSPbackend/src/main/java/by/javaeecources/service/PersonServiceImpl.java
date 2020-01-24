package by.javaeecources.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import by.javaeecources.model.Person;
import by.javaeecources.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	PersonRepository repository;
	
	@Override
	public void deletePersonById(Long id) {
		Optional<Person> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.delete(optional.get());	
		}
		
	}


	@Override
	public Optional<Person> findById(Long id) {
		return  repository.findById(id);
	}

	@Override
	public List<Person> findByFirstname(String name) {
		return repository.findByFirstname(name);
	}
	
	

	@Override
	public Person createOrUpdatePerson(Person person) {
		if(person.getId() == null ||(person.getFirstname()!=null && person.getId() == 0l && !person.getFirstname().isEmpty())) {
			return repository.save(person);
		}else {
			Optional<Person> requestedFromDB = repository.findById(person.getId());
			if(requestedFromDB.isPresent()) {
				Person person2Save = requestedFromDB.get();
				BeanUtils.copyProperties(person, person2Save);
				
				
//				person2Save.setFirstname(person.getFirstname());
//				person2Save.setLastname(person.getLastname());
//				person2Save.setUsername(person.getUsername());
//				person2Save.setEmail(person.getEmail());
//				person2Save.setDescription(person.getDescription());
//				person2Save.setRole(person.getRole());
				return repository.save(person2Save);
			}
		}
		return person;
	}

	public Page<Person> findAllPersonWithPagination(PageRequest pageable, String name) {
		return repository.findAllPersonWithPagination(pageable, name);
	}


	@Override
	public List<Person> findAll() {
			return (List<Person>) repository.findAll();
	}


	@Override
	public List<Person> findByFirstnameOrLastnameOrderById(String firstName, String lastName) {
		return repository.findByFirstnameOrLastnameOrderById(firstName, lastName);
	}


	@Override
	public Long count() {
		return repository.count();
	}


	@Override
	public Person findPersonById(Long id) {
		Optional<Person> optional =repository.findById(id); 
		if(optional.isPresent())
			return optional.get();
		return null;	
	}


	@Override
	public List<Person> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
        Page<Person> pagedResult = repository.findAll(paging);
         
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
	}

	
}
