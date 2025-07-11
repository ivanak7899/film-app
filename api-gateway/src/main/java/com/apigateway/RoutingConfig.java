package com.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("films", r -> r.path("/films/**")
                        .uri("http://film-service:8080"))
                .route("reviews", r -> r.path("/reviews/**")
                        .uri("http://review-service:8080"))
                .build();
    }
}
