package com.fibra.backendfibra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LabTeC - API Fibra")
                        .version("1.0.0")
                        .description("Documentação da API Fibra com Swagger OpenAPI. Desenvolvida pelo LabTeC - Laboratório de Tecnologias Computacionais")
                        .contact(new Contact()
                                .name("LabTeC - Laboratório de Tecnologias Computacionais")
                                .email("labtec@ufra.edu.br")
                                .url("https://labtec.ufra.edu.br")
                        ));
    }
}
