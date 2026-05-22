package com.medvida.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MedVida - Gestão de Clínica Popular")
                        .version("1.0")
                        .description("API para gerenciamento de pacientes, médicos e consultas da MedVida"));
    }
}
