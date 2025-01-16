package com.miti99.demo.circuitbreaker;

import java.util.random.RandomGenerator;

public class DemoService {

  public String api() {
    if (RandomGenerator.getDefault().nextBoolean()) {
      throw new RuntimeException("Error");
    }

    return "Response";
  }
}
