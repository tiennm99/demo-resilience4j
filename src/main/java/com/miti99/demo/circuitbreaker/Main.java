package com.miti99.demo.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  private static final DemoService SERVICE = new DemoService();

  public static void main(String[] args) {
    var config =
        CircuitBreakerConfig.custom()
            .failureRateThreshold(25)
            .slidingWindowSize(5)
            .waitDurationInOpenState(Duration.ofMillis(5))
            .build();

    var registry = CircuitBreakerRegistry.of(config);

    var circuitBreaker = registry.circuitBreaker("demo-circuit-breaker");

    var decorated = CircuitBreaker.decorateSupplier(circuitBreaker, SERVICE::api);

    for (int i = 0; i < 100; i++) {
      log.info("state: {}", circuitBreaker.getState());

      try {
        log.info("response: {}", decorated.get());
      } catch (Exception e) {
        log.error("error", e);
      }
    }
  }
}
