package com.bsoft.adres.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Value("${info.project.version}")
    private String version;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Adres API")
                        .version(version)
                        .description("Adresbook API")
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/license/MIT")
                        )
                        .contact(new Contact()
                                .url("https://bsoft.com/info")
                        )
        );
    }
}
