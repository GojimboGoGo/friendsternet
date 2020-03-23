package jbourne.demo.friendsternet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Set;

@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        final ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Friend Management API")
                .description("Allows for basic friend API operations.")
                .version("1.0.0-SNAPSHOT")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("jbourne.demo.friendsternet"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/connections/**")
                        .or(PathSelectors.ant("/users/**"))
                        .or(PathSelectors.ant("/subscriptions/**"))
                        .or(PathSelectors.ant("/blocklists/**"))
                        .or(PathSelectors.ant("/api/**/**")))
                .build()
                .produces(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .consumes(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .apiInfo(apiInfo);
    }
}
