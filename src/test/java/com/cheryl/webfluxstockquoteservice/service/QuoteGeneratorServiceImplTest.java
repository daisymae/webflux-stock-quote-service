package com.cheryl.webfluxstockquoteservice.service;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import com.cheryl.webfluxstockquoteservice.model.Quote;

import reactor.core.publisher.Flux;

public class QuoteGeneratorServiceImplTest {

  QuoteGeneratorServiceImpl quoteGeneratorServiceImpl = new QuoteGeneratorServiceImpl();

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void fetchQuoteStream() throws Exception {
    // goes through so quick, never see the output
    // get quoteFlux of quotes
    Flux<Quote> quoteFlux = quoteGeneratorServiceImpl.fetchQuoteStream(Duration.ofMillis(100L));

    quoteFlux.take(10).subscribe(System.out::println);
    
    // so can see some output: not elegant way to handle - see next test
    /*
    Thread.sleep(1000);
    Thread.sleep(1000);
    Thread.sleep(1000);
    Thread.sleep(1000);
    */
  }
  
  @Test
  public void fetchQuoteStreamCountDown() throws Exception{
    // get quoteFlux of quotes
    Flux<Quote> quoteFlux = quoteGeneratorServiceImpl.fetchQuoteStream(Duration.ofMillis(100L));
    
    // subscriber lambda
    Consumer<Quote> println = System.out::println;
    
    // error handler
    Consumer<Throwable> errorHandler = e -> System.out.println("Some error occurred");
    
    // set Countdown latch to 1
    // utilizing CountDownLatch is much better than using Thread.sleep
    CountDownLatch countDownLatch = new CountDownLatch(1);
    
    // runnable called upon complete, count down latch
    Runnable allDone = () -> countDownLatch.countDown();
    
    quoteFlux.take(30)
      .subscribe(println, errorHandler, allDone);
    
    // will await for allDone; tells the test to wait for the output
    countDownLatch.await();
  }
}
