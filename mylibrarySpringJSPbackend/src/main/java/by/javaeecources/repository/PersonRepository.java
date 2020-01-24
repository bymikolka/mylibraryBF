package by.javaeecources.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import by.javaeecources.model.Person;
@Repository
public  interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	List<Person> findByFirstname(String name);
	@Override
	Optional<Person> findById(Long id);
	
	
	List<Person> findByFirstnameOrLastnameOrderById(String firstName, String lastName);
	//Optional<Person> findFirst5ByFirstnameStartsWithOrderByFirstname(String firstName, String lastName);

	
	@Query(value = "SELECT p FROM Person p WHERE p.firstname = ?1  ORDER BY id")
	Page<Person> findAllPersonWithPagination(Pageable pageable, String name);

	
}
