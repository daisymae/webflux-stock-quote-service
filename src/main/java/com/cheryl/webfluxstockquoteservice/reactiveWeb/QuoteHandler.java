package com.cheryl.webfluxstockquoteservice.reactiveWeb;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.cheryl.webfluxstockquoteservice.model.Quote;
import com.cheryl.webfluxstockquoteservice.service.QuoteGeneratorService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author corcutt
 *
 */
@Component
public class QuoteHandler {

  private final QuoteGeneratorService quoteGeneratorService;

  public QuoteHandler(QuoteGeneratorService quoteGeneratorService) {
    this.quoteGeneratorService = quoteGeneratorService;
  }
  
  /**
   * returning a Mono of a Flux stream which will be in the ServerResponse
   * 
   * @param request
   * @return
   */
  public Mono<ServerResponse> fetchQuotes(ServerRequest request) {
    int size = Integer.parseInt(request.queryParam("size").orElse("10"));
    
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(this.quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100))
            .take(size), Quote.class);
  }
  
  /**
   * handle the request for stream of quotes
   * 
   * @param request
   * @return
   */
  public Mono<ServerResponse> streamQuotes(ServerRequest request) {
    return ok().contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(this.quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(200)), Quote.class);
  }
}
