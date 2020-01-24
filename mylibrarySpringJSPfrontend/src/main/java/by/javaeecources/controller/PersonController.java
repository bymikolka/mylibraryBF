package by.javaeecources.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import by.javaeecources.model.Person;
import by.javaeecources.model.PersonDto;
@Controller
public class PersonController {


	
	//@Autowired
   // private ApplicationEventPublisher eventPublisher;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 5, 10, 25, 50 };
	
	Map<Long, String> mapRoles = null;
	public Map<Long, String> getRoles() {
		if(mapRoles!=null) {
			return mapRoles;
		}
		mapRoles = new HashMap<>();
	    mapRoles.put(1L, "Teacher");
	    mapRoles.put(2L, "Student");
	    return mapRoles;
	}
	
	final String url = 	"http://localhost:8100"; 
	
	@GetMapping
    public String home(@RequestParam("recordsPerPage") Optional<Integer> pageSize, 
    		@RequestParam("currentPage") Optional<Integer> page, 
            @RequestParam(defaultValue = "id") String sortBy,
            final UriComponentsBuilder uriBuilder,
            final HttpServletResponse response,
    		Model model) throws IOException {
		 
		RestTemplate restTemplate = new RestTemplate();
		
		int recordsPerPage = pageSize.orElse(INITIAL_PAGE_SIZE);
		int tempPageNumber = page.isPresent()?page.get()-1:1;
        int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : tempPageNumber;

        ResponseEntity<List<Person>> responseEntity =
                restTemplate.exchange(
                        url+"/api/persons",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {
                        });

        
        List<Person> personsList = responseEntity.getBody();
        assert personsList != null;

        

        //personsList.forEach(System.out::println);

        
        //List<Person> personsList = personService.findAll(currentPage, recordsPerPage, sortBy);
        long rows = 
                restTemplate.exchange(
                        url+"/api/persons/count",
                        HttpMethod.GET,
                        null,
                        Integer.class).getBody();
        
		int nOfPages = (int) (rows / recordsPerPage);
		if (nOfPages % recordsPerPage > 0 && (nOfPages * recordsPerPage != rows)) {
			nOfPages++;
		}

        //eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Person>(Person.class, uriBuilder, response, currentPage, nOfPages, recordsPerPage));

		
		model.addAttribute("noOfPages", nOfPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("recordsPerPage", recordsPerPage);
		model.addAttribute("pageSizes", PAGE_SIZES);
        
        
        model.addAttribute("personsList",personsList);
        model.addAttribute("mapRoles", getRoles());

        return "index";
    }
	
    @GetMapping("/search")
    public String search(@ModelAttribute("person") PersonDto personDto, Model model) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Person>> response =
                restTemplate.exchange(
                        url+"/api/search/"+person.getFirstname(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {
                        });

        List<Person> personsList = response.getBody();
		//List<Person> personsList = personService.findByFirstnameOrLastnameOrderById(person.getFirstname(), person.getFirstname());
		model.addAttribute("personsList",personsList);
		model.addAttribute("mapRoles", mapRoles);
        return "index";
    }
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		new RestTemplate().exchange(
                        url+"/api/"+id,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {
                        });
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/create")
	public String create(Model model) {
		model.addAttribute("mapRoles", mapRoles);
		return "newPerson";
	}

	@Transactional
    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") PersonDto personDto) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		
		new RestTemplate().postForObject(url, person, Person.class);
		
        return "redirect:/";
    }
	
	@GetMapping(path = { "edit", "/edit/?id={id}" })
	public String update(Model model, @RequestParam("id") Optional<Long> id) {
	    model.addAttribute("mapRoles", mapRoles);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
 
        RestTemplate restTemplate = new RestTemplate();

	    
	    if (id.isPresent()) {

	    	 ResponseEntity<Person> p = new RestTemplate().exchange(
                    url+"/api/"+id.get(),
                    HttpMethod.GET,
                    null,
                    Person.class);
	    	
	        if (p.getBody() != null) {
	        	Person person = p.getBody();
	        	System.out.println(person);
				model.addAttribute("person", person);
	        }
	        model.addAttribute("mapRoles", mapRoles);
		}

		return "newPerson";
	}
	
/*
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		personService.delete(id);
		return "redirect:/";
	}
	
	
	@Transactional
    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") PersonDto personDto) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		personService.createOrUpdatePerson(person);
        return "redirect:/";
    }
    @GetMapping("/search")
    public String search(@ModelAttribute("person") PersonDto personDto, Model model) {
		Person person = new Person();
		BeanUtils.copyProperties(personDto, person);
		List<Person> personsList = personService.findByFirstnameOrLastnameOrderById(person.getFirstname(), person.getFirstname());
		model.addAttribute("personsList",personsList);
		model.addAttribute("mapRoles", mapRoles);
        return "index";
    }
	
	@PostMapping(value = "/create")
	public String create(Model model) {
		model.addAttribute("mapRoles", mapRoles);
		return "newPerson";
	}


	@GetMapping(path = { "edit", "/edit/?id={id}" })
	public String update(Model model, @RequestParam("id") Optional<Long> id) {
	    model.addAttribute("mapRoles", mapRoles);
		if (id.isPresent()) {
			Optional<Person> personOptional = personService.findById(id.get());
			if (personOptional.isPresent()) {
				model.addAttribute("person", personOptional.get());
			}
		}

		return "newPerson";
	}
	*/
}
