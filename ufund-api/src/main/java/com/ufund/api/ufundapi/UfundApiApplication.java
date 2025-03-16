package com.ufund.api.ufundapi;

import java.io.IOException;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UfundApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfundApiApplication.class, args);
		try{
			java.awt.Desktop.getDesktop().browse(URI.create("http://localhost:8080/"));
		}
		catch (IOException e){
			e.getStackTrace();
		}
	}

}
