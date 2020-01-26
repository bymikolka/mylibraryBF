package by.javaeecources.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import by.javaeecources.model.Person;
import by.javaeecources.model.PersonDto;
import by.javaeecources.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 5, 10, 25, 50 };

	Map<Long, String> mapRoles = null;

	public Map<Long, String> getRoles() {
		if (mapRoles != null) {
			return mapRoles;
		}
		mapRoles = new HashMap<>();
		mapRoles.put(1L, "Teacher");
		mapRoles.put(2L, "Student");
		return mapRoles;
	}

	/*
	 * @GetMapping("/api/persons") public ResponseEntity<List<Person>> home() {
	 * return ResponseEntity.ok(personService.findAll());
	 * 
	 * }
	 */

	@GetMapping({ "/api/persons", "/api/persons/recordsPerPage={recordsPerPage}&currentPage={currentPage}" })
	public ResponseEntity<List<Person>> home(@RequestParam("recordsPerPage") Optional<Integer> pageSize,
			@RequestParam("currentPage") Optional<Integer> page, @RequestParam(defaultValue = "id") String sortBy,
			Model model) {

		int recordsPerPage = pageSize.orElse(INITIAL_PAGE_SIZE);
		int tempPageNumber = page.isPresent() ? page.get() - 1 : 1;
		int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : tempPageNumber;

		List<Person> personsList = personService.findAll(currentPage, recordsPerPage, sortBy);
		long rows = personService.count();

		long nOfPages = rows / recordsPerPage;
		if (nOfPages % recordsPerPage > 0 && (nOfPages * recordsPerPage != rows)) {
			nOfPages++;
		}

		model.addAttribute("noOfPages", nOfPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("recordsPerPage", recordsPerPage);
		model.addAttribute("pageSizes", PAGE_SIZES);

		model.addAttribute("personsList", personsList);
		model.addAttribute("mapRoles", getRoles());

		return ResponseEntity.ok(personsList);

	}

	@GetMapping("/api/persons/count")
	public ResponseEntity<Integer> countData() {
		return ResponseEntity.ok(personService.findAll().size());

	}

	@PostMapping // consumes = "application/json", produces = "application/json"
	public ResponseEntity<Void> createNewPerson(@Valid @RequestBody PersonDto personDto,
			UriComponentsBuilder uriComponentsBuilder) {
		System.out.println("here we are!!!!");
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		person = personService.createOrUpdatePerson(person);
		// host and port
		UriComponents uriComponents = uriComponentsBuilder.path("/api/person/{id}").buildAndExpand(person.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
		personService.deletePersonById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/persons/{id}")
	public ResponseEntity<Person> findPersonById(@PathVariable("id") Long id) {
		Person person = personService.findPersonById(id);

		if (person != null) {
			System.out.println(person);
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/api/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonDto personDto) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		person = personService.createOrUpdatePerson(person);
		if (person == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(person);

	}

	@GetMapping("/api/search/{name}")
	@ResponseBody

	public ResponseEntity<List<Person>> search(@PathVariable("name") String name) {
		List<Person> personsList = personService.findByFirstnameOrLastnameOrderById(name, name);
		return ResponseEntity.ok(personsList);
	}

}
