package uz.avotech.layer.project.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static uz.avotech.layer.project.config.ProjectConstants.PROJECT_NAME;

@Configuration
public class SwaggerConfig {

    public static final String ACTUATOR_PATH = "/actuator/**";
    public static final String ACTUATOR_PREFIX = "-actuator";

    @Bean
    HandlerMapping mapping() {
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public GroupedOpenApi publicActuatorApi() {
        return GroupedOpenApi.builder()
                .group(PROJECT_NAME + ACTUATOR_PREFIX)
                .pathsToMatch(ACTUATOR_PATH)
                .build();
    }

}
