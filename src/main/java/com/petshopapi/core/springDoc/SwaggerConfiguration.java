package com.petshopapi.core.springDoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Shop API")
                        .version("v1")
                        .description("Sistema de controle e gerenciamento de uma loja Pet Shop.")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("Petshop")
                        .url("https://petshop.com.br")
                );
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .stream()
                    .flatMap(pathItem -> pathItem.readOperations().stream())
                    .forEach(operation -> {
                        ApiResponses responses = operation.getResponses();

                        ApiResponse apiResponseNaoEncontrado = new ApiResponse().description("Recurso não encontrado");
                        ApiResponse apiResponseErroInterno = new ApiResponse().description("Erro interno no servidor");
                        ApiResponse apiResponseSemRepresentacao = new ApiResponse()
                                .description("Recurso não possui uma representação que poderia ser aceita pelo consumidor");

                        responses.addApiResponse("404", apiResponseNaoEncontrado);
                        responses.addApiResponse("406", apiResponseSemRepresentacao);
                        responses.addApiResponse("500", apiResponseErroInterno);
                    });
        };
    }
}
