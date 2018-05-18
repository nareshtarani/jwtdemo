package org.jwtdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("org.jwtdemo"))
				.paths(PathSelectors.any()).build().apiInfo(metaData()).securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiInfo metaData() {

		ApiInfo apiInfo = new ApiInfoBuilder().title("Spring Boot REST API")
				.description("Spring Boot REST API for Application")
				.contact(new Contact("Naresh Tarani", "http://nareshtarani.wordpress.com", "nareshtarani@github.com"))
				.license("GPL").version("1.0.0").build();
		return apiInfo;
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", "");
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}
}