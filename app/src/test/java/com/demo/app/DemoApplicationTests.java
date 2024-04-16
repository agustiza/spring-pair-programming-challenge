package com.demo.app;

import com.demo.carsales.analytics.service.CarPurchasingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(args = {"--start=2023-01-01T00:00:00Z", "--end=2024-01-01T00:00:00Z"})
class DemoApplicationTests {

  @Autowired
  CarPurchasingService service;

  @Test
  void contextLoads() {
    assertNotNull(service);
  }

}
