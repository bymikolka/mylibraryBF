package by.javaeecources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "by.javaeecources.service", "by.javaeecources.controller" })
@EnableJpaRepositories("by.javaeecources.repository")
@EntityScan(basePackageClasses = { MylibrarySpringJspApplication.class })
public class MylibrarySpringJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(MylibrarySpringJspApplication.class, args);
	}

}
