package com.lintik.inventarioservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient productosWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/api/productos")
                .build();
    }
}