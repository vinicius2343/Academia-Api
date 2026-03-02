package com.academia.apiacademia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI
 * Define as informações da API e documentação
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura a documentação OpenAPI da aplicação
     * @return OpenAPI configurada com informações do projeto
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Academia - Sistema de Gestão")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de academia. " +
                                "Sistema profissional com suporte a múltiplos usuários (alunos, professores, administradores).")
                        .contact(new Contact()
                                .name("Academia Local")
                                .email("contato@academia.com")
                                .url("https://www.academia.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

