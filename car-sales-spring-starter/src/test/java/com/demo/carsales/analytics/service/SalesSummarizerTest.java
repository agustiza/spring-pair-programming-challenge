package com.demo.carsales.analytics.service;

import com.demo.carsales.analytics.model.Car;
import com.demo.carsales.analytics.model.CarPurchasedEvent;
import com.demo.carsales.analytics.repository.CarPurchasedEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesSummarizerTest {

    @Mock
    CarPurchasedEventRepository events;

    @BeforeEach
    void setUpBefore() {
      when(events.findCarPurchasedEventByDateBetween(any(), any()))
        .thenReturn(Stream.of(
          new CarPurchasedEvent(ZonedDateTime.parse("2024-04-10T10:00:00Z"), 100, new Car(1, "A", "A", 2024, 100, null)),
          new CarPurchasedEvent(ZonedDateTime.parse("2024-04-15T11:00:00Z"), 120, new Car(2, "B", "B", 2024, 100, null)),
          new CarPurchasedEvent(ZonedDateTime.parse("2024-03-20T09:00:00Z"), 110, new Car(3, "B", "B", 2024, 100, null)),
          new CarPurchasedEvent(ZonedDateTime.parse("2024-03-25T10:30:00Z"), 130, new Car(4, "A", "B", 2024, 100, null)),
          new CarPurchasedEvent(ZonedDateTime.parse("2024-02-05T14:00:00Z"), 120, new Car(5, "A", "B", 2024, 100, null))
        ));
    }


    @Test
    public void summarizeCarPurchaseEvents() {

        //When the data is summarized
        List<MonthlyCarSaleByBrandRow> summary = new SalesSummarizer(events)
          .sumTotalSalesByMonthAndBrand(
            ZonedDateTime.parse("2024-02-05T14:00:00Z"),
            ZonedDateTime.parse("2024-04-10T10:00:00Z"));


        //Then return a sum of cars sold by amount grouping by yearMonth brand
        String stringSummary = summary.stream()
            .map(MonthlyCarSaleByBrandRow::toString)
            .collect(Collectors.joining("\n", "Brand, Month, Total Cars Sold\n", ""));
        assertEquals("""
            Brand, Month, Total Cars Sold
            B, 2024-02, 120.0
            B, 2024-03, 240.0
            A, 2024-04, 100.0
            B, 2024-04, 120.0""",
            stringSummary);

    }
}

