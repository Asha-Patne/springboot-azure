package com.java.springboot.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@SecurityScheme(name = "basicauth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "User API", version = "2.0", description = "User Information"))
@Log4j2
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		log.info("Welcome to Spring Boot Application with Swagger and deployed to Azure...!!!!");
	}

}
