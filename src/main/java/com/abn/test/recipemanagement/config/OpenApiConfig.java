package com.abn.test.recipemanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Recipe Management REST API",
                version = "0.1.0"
        )
)
public class OpenApiConfig {
}
