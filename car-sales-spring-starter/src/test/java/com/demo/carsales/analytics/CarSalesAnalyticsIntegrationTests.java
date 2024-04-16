package com.demo.carsales.analytics;

import com.demo.carsales.analytics.model.Car;
import com.demo.carsales.analytics.model.CarPurchasedEvent;
import com.demo.carsales.analytics.repository.CarPurchasedEventRepository;
import com.demo.carsales.analytics.repository.CarRepository;
import com.demo.carsales.analytics.service.MonthlyCarSaleByBrandRow;
import com.demo.carsales.analytics.service.SalesSummarizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
  "spring.jpa.generate-ddl=true",
  "spring.jpa.show-sql=true",
  "spring.jpa.properties.hibernate.jdbc.fetch_size=100",
  "spring.jpa.properties.hibernate.jdbc.batch_size=100",
  "spring.jpa.properties.hibernate.order_updates=true",
  "spring.jpa.properties.hibernate.order_inserts=true"
})
class CarSalesAnalyticsIntegrationTests {

  @EnableAutoConfiguration
  @Configuration
  public static class TestConfiguration { }

  @Autowired
  CarRepository repository;

  @Autowired
  CarPurchasedEventRepository events;

  @Autowired
  SalesSummarizer salesSummarizer;

  private final List<MonthlyCarSaleByBrandRow> expected = List.of(
    new MonthlyCarSaleByBrandRow("A", YearMonth.of(2023, 1), 1000.0),
    new MonthlyCarSaleByBrandRow("B", YearMonth.of(2023, 1), 1000.0),
    new MonthlyCarSaleByBrandRow("B", YearMonth.of(2023, 2), 2000.0),
    new MonthlyCarSaleByBrandRow("A", YearMonth.of(2023, 3), 1000.0)
  );

  private ZonedDateTime start = ZonedDateTime.parse("2023-01-01T00:00:00Z");

  @BeforeEach
  @Transactional
  void setupBefore() {
    // Clear existing data from the repositories
    repository.deleteAll();
    events.deleteAll();

    // Populate the database with sample data
    List<Car> cars = IntStream.range(0, 5)
      .mapToObj(id -> new Car("A", id % 4 == 0 ? "A" : "B", 2024, 1000, null))
      .toList();
    repository.saveAll(cars);

    // Add purchase events for the cars
    events.saveAll(cars.stream()
      .map(car -> new CarPurchasedEvent(start.plusWeeks(2 * car.getId()), 1000, car))
      .toList());
  }

  /**
   * Given
   *  A DB with car events
   * When
   *  A service method is called
   * Then
   *  Expect the Spring Context to load successfully
   *  And
   *  Results to match the expectation
   */
  @Test
  @Transactional
  void okSalesAreSummarizedByMonthBrandIntegrationTest() {
    List<MonthlyCarSaleByBrandRow> summedSales =
      salesSummarizer.sumTotalSalesByMonthAndBrand(start, start.plusYears(1));

    assertThat(summedSales)
      .usingRecursiveFieldByFieldElementComparator()
      .isEqualTo(expected);
  }

}
