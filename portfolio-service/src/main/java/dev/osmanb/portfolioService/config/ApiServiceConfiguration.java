package dev.osmanb.portfolioService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiServiceConfiguration {
    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
