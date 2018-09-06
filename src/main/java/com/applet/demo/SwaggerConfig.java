package com.applet.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getAipInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.applet.demo"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getAipInfo() {
		return new ApiInfoBuilder()
				.title("微信小程序项目API")
				.description("微信小程序项目API")
				.version("1.0")
				.build();
	}
}
