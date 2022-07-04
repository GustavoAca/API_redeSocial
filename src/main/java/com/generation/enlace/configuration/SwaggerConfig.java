package com.generation.enlace.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI criarOpenAPI() {

		return new OpenAPI()
				.info(new Info()
						.title("Projeto Enlace")
						.description("Enlace é uma rede social que conecta pessoas querendo ajudar com organizações precisando de ajuda, criada como projeto integrador do bootcamp da Generation Brasil. ")
						.version("v0.0.1") 
				.license(new License()
								.name("Attribution 4.0 International (CC BY 4.0) ")
								.url("https://creativecommons.org/licenses/by/4.0/")) 
				.contact(new Contact() 
								.name("Equipe Enlace")
								.url("https://github.com/enlaceorg/enlace-projeto-integrador")))
				.externalDocs(new ExternalDocumentation()
						.description("GitHub")
						.url("https://github.com/enlaceorg"));
	};

	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}
}
