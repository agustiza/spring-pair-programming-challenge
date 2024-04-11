package com.demo.springpairprogramming.service;

import com.demo.springpairprogramming.model.Car;
import com.demo.springpairprogramming.model.CarPurchasedEvent;
import com.demo.springpairprogramming.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SalesSummarizerTest {

    private final CarRepository mockRepository = new CarRepository() {

        private final Map<Integer, Car> cars = new HashMap<>();
        {
            cars.put(1, new Car(1, "model", "A", 2024, 100));
            cars.put(2, new Car(2, "model", "B", 2024, 100));
            cars.put(3, new Car(3, "model", "A", 2024, 100));
        }

        @Override
        public Optional<Car> findOne(int id) {
            return Optional.of(cars.get(id));
        }

        @Override
        public void deleteCarById(int id) {}

        @Override
        public void insert(Car newCar) {}
    };


    @Test
    public void summarizeCarPurchaseEvents() {
        //Given
        //A list of car events
        List<CarPurchasedEvent> carEvents = List.of(
            new CarPurchasedEvent(ZonedDateTime.parse("2024-04-10T10:00:00Z"), 100, 1),
            new CarPurchasedEvent(ZonedDateTime.parse("2024-04-15T11:00:00Z"), 120, 2),
            new CarPurchasedEvent(ZonedDateTime.parse("2024-03-20T09:00:00Z"), 110, 1),
            new CarPurchasedEvent(ZonedDateTime.parse("2024-03-25T10:30:00Z"), 130, 3),
            new CarPurchasedEvent(ZonedDateTime.parse("2024-02-05T14:00:00Z"), 120, 2)
        );

        //When the data is summarized
        List<MonthlyCarSaleByBrandRow> summary = new SalesSummarizer(mockRepository)
                .calcSalesByMonthAndBrand(carEvents);


        //Then return a sum of cars sold by amount grouping by month brand
        String stringSummary = summary.stream()
            .map(MonthlyCarSaleByBrandRow::toString)
            .collect(Collectors.joining("\n"));
        assertEquals("""
            Brand: B, Month: FEBRUARY, Monthly total: 120
            Brand: A, Month: MARCH, Monthly total: 240
            Brand: A, Month: APRIL, Monthly total: 100
            Brand: B, Month: APRIL, Monthly total: 120""",
            stringSummary);

    }
}

