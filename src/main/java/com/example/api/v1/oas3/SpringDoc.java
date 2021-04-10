package com.example.api.v1.oas3;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Component
public class SpringDoc {

    @Bean
    public OpenAPI openAPI() {
        final Contact contact = new Contact().name("Example").email("support@example.com");
        final Info info = new Info().title("API Reference").version("1.0").contact(contact);
        final OpenAPI openAPI = new OpenAPI().info(info);
        return openAPI;
    }

}
