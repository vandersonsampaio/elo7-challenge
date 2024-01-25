package br.com.elo7.sonda.candidato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SevenMarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SevenMarsApplication.class, args);
		
	}

}
