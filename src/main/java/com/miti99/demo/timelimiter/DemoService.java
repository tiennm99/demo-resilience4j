package com.miti99.demo.timelimiter;

import java.util.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoService {

  public String api() throws InterruptedException {
    if (RandomGenerator.getDefault().nextBoolean()) {
      log.info("service pending");
      Thread.sleep(3_000);
      log.info("service running");
    }

    return "Response";
  }
}
