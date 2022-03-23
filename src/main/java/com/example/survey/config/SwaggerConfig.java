package com.example.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("SurveyAPI")
                        .description("API for the user survey system")
                        .version("1.0.0")
                        .contact(new Contact("Bulat", "", ""))
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.survey.controller"))
                .paths(PathSelectors.any())
                .build()
                .tags(
                        new Tag("Для администратора", "Управление опросами для администратора"),
                        new Tag("Для пользователей", "Прохождение опросов для пользователей")
                );
    }
}
