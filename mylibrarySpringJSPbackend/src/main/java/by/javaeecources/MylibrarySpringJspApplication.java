package by.javaeecources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication//(scanBasePackages = { "by.javaeecources.service", "by.javaeecources.controller" })
//@EnableJpaRepositories("by.javaeecources.repository")
//@EntityScan(basePackageClasses = { MylibrarySpringJspApplication.class })
@EnableAspectJAutoProxy
public class MylibrarySpringJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(MylibrarySpringJspApplication.class, args);
	}

}
