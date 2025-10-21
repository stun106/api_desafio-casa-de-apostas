package api.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DesafioFullstackCasaDeApostasApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioFullstackCasaDeApostasApplication.class, args);
	}

}
