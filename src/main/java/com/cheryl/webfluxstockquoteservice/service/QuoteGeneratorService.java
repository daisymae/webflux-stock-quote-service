package com.cheryl.webfluxstockquoteservice.service;

import java.time.Duration;

import com.cheryl.webfluxstockquoteservice.model.Quote;

import reactor.core.publisher.Flux;

public interface QuoteGeneratorService {
  
  Flux<Quote> fetchQuoteStream(Duration period);

}
