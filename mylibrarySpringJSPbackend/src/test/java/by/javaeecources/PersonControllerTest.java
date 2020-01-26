package by.javaeecources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.javaeecources.controller.PersonController;
import by.javaeecources.exception.PersonNotFoundException;
import by.javaeecources.model.Person;
import by.javaeecources.service.PersonService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private PersonService personService;

	@Captor
	private ArgumentCaptor<Person> argumentCaptor;
	
	final String url = "http://localhost:8100";
	
	
	@Test
	public void postingANewPersonShouldCreateANewPersonInTheDatabase() throws Exception {
		Person personDto = new Person();
		personDto.setId(999L);
		personDto.setFirstname("Billy");
		personDto.setLastname("Dilly");
		personDto.setUsername("Willy");
		personDto.setRole(1L);
		personDto.setEmail("bmw@gmail.com");
		personDto.setDescription("Duck tales");
		
		// перехват метода и возврат 999
		when(personService.createOrUpdatePerson(argumentCaptor.capture())).thenReturn(personDto);
		this.mockMvc
				.perform(post("/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(personDto)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(header().string("Location", "http://localhost/api/person/999"));

		assertEquals("Billy", argumentCaptor.getValue().getFirstname());
		assertEquals("Dilly", argumentCaptor.getValue().getLastname());
	}
	
	
	@Test
	public void getPersonWithIdShouldReturnAPerson()  throws Exception {
		when(personService.findPersonById(8L)).thenReturn(createPerson(8L, "Chelsea", "Lueilwitz", "clayton.heathcote", 1L, "megan.dubuque@hotmail.com", "Ironbarrow Technical College"));

			this.mockMvc
					.perform(get("/api/{id}", 8))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.firstname").value("Chelsea"))
					.andExpect(jsonPath("$.lastname").value("Lueilwitz"))
					.andExpect(jsonPath("$.username").value("clayton.heathcote"))
					.andExpect(jsonPath("$.role").value(1L))
					.andExpect(jsonPath("$.email").value("megan.dubuque@hotmail.com"))
					.andExpect(jsonPath("$.description").value("Ironbarrow Technical College"));
	}



	private Person createPerson(Long id, String firstname, String lastname, String username, Long role, String email,
			String description) {
		Person person = new Person();
		person.setId(id);
		person.setFirstname(firstname);
		person.setLastname(lastname);
		person.setUsername(username);
		person.setRole(role);
		person.setEmail(email);
		person.setDescription(description);
		
		
		return person;
	}
	
	@Test
	public void getPersonWithUnknownIdShouldReturn404()  throws Exception {
		when(personService.findPersonById(420L)).thenThrow(new PersonNotFoundException("Person with id '420' not found"));

			this.mockMvc
					.perform(get("/api/{id}", 420L))
					.andExpect(status().isNotFound());
	}
	
	
}
