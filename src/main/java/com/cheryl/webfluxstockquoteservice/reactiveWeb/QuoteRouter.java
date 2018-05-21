package com.cheryl.webfluxstockquoteservice.reactiveWeb;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
/**
 * 
 * @author corcutt
 *
 */
@Configuration
public class QuoteRouter {
  
  /**
   * becomes a Spring Bean inside the configuration
   * will get QuoteHandler injected from Spring context
   * QuoteHandler is defined as a spring-managed component
   * This will wire it up by the Spring Contex
   * @param handler
   * @return
   */
  @Bean
  public RouterFunction<ServerResponse> route(QuoteHandler handler) {
    return RouterFunctions.route(GET("/quotes")
        .and(accept(MediaType.APPLICATION_JSON)), handler::fetchQuotes);
  }

}
