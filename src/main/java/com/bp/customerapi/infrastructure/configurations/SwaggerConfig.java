package com.bp.customerapi.infrastructure.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    
    private static final String GROUP = "customers";
    private static final String BASE_PACKAGE = "com.bp.customerapi.presentation.controllers";
    private static final String API_TITLE = "Customers management API";
    private static final String API_DESCRIPTION = "API REST to management Customers";
    private static final String CONTACT_NAME = "Lucas Gomes Santiago";
    private static final String CONTACT_GITHUB = "https://github.com/lucasgsantiago";
    private static final String CONTACT_EMAIL = "lucasgomessantiago@gmail.com";

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group(GROUP)
                .pathsToMatch("/api/v1/**")
                .packagesToScan(BASE_PACKAGE)
                .build();
    }


    @Bean
    public OpenAPI buildApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version("1.0.0")
                        .contact(new Contact().name(CONTACT_NAME).email(CONTACT_EMAIL).url(CONTACT_GITHUB))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
