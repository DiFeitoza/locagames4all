package ufc.pds.locagames4all;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Locagames4allApplication {

    public static void main(String[] args) {
        System.out.println("Programa LocaGames4all iniciado.");
        SpringApplication.run(Locagames4allApplication.class, args);
    }

    @Bean
    public OpenAPI LocaGames4all() {
        return new OpenAPI()
                .info(new Info().title("LocaGames4all API")
                        .description("Sua API de locação de jogos de mesa!")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}