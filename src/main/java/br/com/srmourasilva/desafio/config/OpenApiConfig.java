package br.com.srmourasilva.desafio.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(
                new Info().title("Users microsservice")
                    .description("Microservice for managing users and authentication")
                    .version("v1.0.0")
                    .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                    //.termsOfService("http://swagger.io/terms/")
                    .contact(
                        new Contact()
                            .name("Paulo Mateus")
                            .url("https://github.com/SrMouraSilva/Desafio-CRUD-usuario")
                            .email("mateus.moura at hotmail.com")
                    )
            )
            .components(
                new Components()
                    .addSecuritySchemes(
                        "Bearer",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .description("User token")
                            .scheme("Authorization")
                            .bearerFormat("JWT")
                    )
                )
            ;
    }
}
