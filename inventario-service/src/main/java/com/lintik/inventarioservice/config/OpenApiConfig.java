package com.lintik.inventarioservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("productos-api")  // Puedes cambiar el nombre del grupo según tu preferencia
                .packagesToScan("com.lintik.inventarioservice")  // Asegúrate de que sea el paquete correcto
                .build();
    }
}
