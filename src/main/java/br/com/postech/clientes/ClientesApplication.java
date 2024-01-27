package br.com.postech.clientes;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientesApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Postech - Lanchonete do Bairro 🍔 - Clientes")
						.description("Simples e eficiente, a API de Clientes possibilita a criação e busca de clientes por CPF, facilitando a gestão da base de clientes em diferentes partes do sistema.")
						.contact(new Contact().name("Daniel Maria da Silva").url("https://github.com/postech-lanchonete"))
						.license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
						.version("1.0.0-POC"));
	}
}
