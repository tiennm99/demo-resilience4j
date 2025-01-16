package com.miti99.demo.timelimiter;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  private static final ExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
  private static final DemoService SERVICE = new DemoService();

  public static void main(String[] args) throws InterruptedException {
    var config =
        TimeLimiterConfig.custom()
            .cancelRunningFuture(false)
            .timeoutDuration(Duration.ofMillis(2500))
            .build();
    var timeLimiterRegistry = TimeLimiterRegistry.of(config);
    var timeLimiter = timeLimiterRegistry.timeLimiter("demo-timelimiter");

    try {
      var response = timeLimiter.executeFutureSupplier(() -> EXECUTOR.submit(SERVICE::api));

      log.info("response: {}", response);
    } catch (Exception e) {
      log.error("error", e);
    }

    Thread.sleep(5_000);

    EXECUTOR.shutdown();
  }
}
