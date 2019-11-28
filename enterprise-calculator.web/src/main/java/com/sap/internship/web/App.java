package com.sap.internship.web;

import java.io.IOException;
import java.util.Collections;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@ComponentScan(basePackages = { "com.sap.internship.web" })
@Import({ com.sap.internship.core.PostfixCalculator.class, com.sap.internship.core.Parser.class,
		com.sap.internship.core.Validator.class, com.sap.internship.core.PostfixConverter.class})
public class App {
	public static int DEFAULT_PORT = 8080;

	public static void main(String[] args) throws IOException, JSONException {
		SpringApplication application = new SpringApplication(App.class);
		String port = System.getenv("CALCULATOR_PORT");
		if (port == null) {
			port = String.valueOf(DEFAULT_PORT);
		}
		application.setDefaultProperties(Collections.singletonMap("server.port", port));
		application.run(args);
	}
}

