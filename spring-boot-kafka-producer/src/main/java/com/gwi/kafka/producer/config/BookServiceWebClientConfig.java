package com.gwi.kafka.producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Configuration
public class BookServiceWebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceWebClientConfig.class);

    @Value("${book.service.base-url}")
    private String bookServiceBaseUrl;

    @Bean("bookServiceWebClient")
    public WebClient bookServiceWebClient() {
        return WebClient.builder()
                .baseUrl(bookServiceBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            LOGGER.info("🔵 WebClient Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> LOGGER.info("{}: {}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            LOGGER.info("🟢 WebClient Response: Status {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
