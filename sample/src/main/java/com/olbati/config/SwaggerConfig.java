package com.olbati.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration class for swagger.
 *
 * @author MAZIGH Med Belhassen
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.olbati.controllers"))
//                .paths(PathSelectors.ant("/email/*"))
                .apis(RequestHandlerSelectors.basePackage("com.olbati"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Email REST API",
                "Send email by SMTP server send email and archive 'it.",
                "API TOS",
                "Terms of service",
                new Contact("MAZIGH Med Belhassen", "http://www.olbati.com/", "mohamed-belhassen.mazigh@olbati.com"),
                "License of API",
                "API license URL");
        return apiInfo;
    }
}
