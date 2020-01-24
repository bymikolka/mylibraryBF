package by.javaeecources.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table( name = "person" )
//@DynamicInsert
@DiscriminatorColumn(
	    name="dtype",
	    discriminatorType=DiscriminatorType.STRING
	)
public class Person {

	
	@NotNull
	@Column(name="username", nullable = false)
	private String username;
	@NotNull
	@Column(name="firstname", nullable = false)
	private String firstname;
	@NotNull
	@Column(name="lastname", nullable = false)
	private String lastname;
	@NotNull
	@Column(name="description",nullable = false)
	private String description;
	@NotNull
	@Column(name="email",nullable = false)
	private String email;
	@NotNull
	@Column(name="role",nullable = false)
	private Long role = 0L;
	
	
	@Column(name="dtype",nullable = false, insertable = false, updatable = false)
	private String dtype;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@SequenceGenerator(name = "pk_sequence", sequenceName = "person_id_seq", allocationSize = 1)
	private Long id = 0L;

	public Person() {
		// Auto-generated constructor stub
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public Person(Long id, String firstname, String lastname, String username, String email, String description) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.role = getRole();
	}

	public Person(Long id, String firstname, String lastname, String username, String role, String email,
			String description) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.role = Long.parseLong(role);
	}

	public String getFullName() {
		return String.format("%s %s", this.firstname, this.lastname);
	}

	public Long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public String getUsername() {
		return this.username;
	}

	public Long getRole() {
		return this.role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Person [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", description="
				+ description + ", email=" + email + ", role=" + role + ", dtype=" + dtype + ", id=" + id + "]";
	}

	
	
//	public IPerson cloneObj(IPerson person) {
//		this.setFirstname(person.getFirstname());
//		this.setUsername(person.getUsername());
//		this.setLastname(person.getLastname());
//		this.setDescription(person.getDescription());
//		this.setEmail(person.getEmail());
//		this.setRole(person.getRole());
//		return this;
//	}


}
