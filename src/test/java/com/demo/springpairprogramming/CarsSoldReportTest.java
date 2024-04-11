package com.demo.springpairprogramming;

import com.demo.springpairprogramming.model.Car;
import com.demo.springpairprogramming.model.CarPurchasedEvent;
import com.demo.springpairprogramming.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarsSoldReportTest {

    private CarRepository mockRepository = new CarRepository() {

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

        //When data is summarized
        List<MonthlyBrandPurchaseSummary> summary = carEvents.stream()
            .collect(
                groupingBy(event -> new GroupingKey(mockRepository.findOne(event.carId()).get().getBrand(), event.date().getMonth()),
                TreeMap::new,
                Collectors.summingInt(CarPurchasedEvent::purchasePrice)))
            .entrySet().stream()
            .map(entry -> new MonthlyBrandPurchaseSummary(
                entry.getKey().brand(),
                entry.getKey().month(),
                entry.getValue()))
            .toList();

        String stringSummary = summary.stream()
            .map(MonthlyBrandPurchaseSummary::toString)
            .collect(Collectors.joining("\n"));

        //Then return a sum of cars sold by amount grouping by month brand
        assertEquals("""
            Brand: B, Month: FEBRUARY, Monthly total: 120
            Brand: A, Month: MARCH, Monthly total: 240
            Brand: A, Month: APRIL, Monthly total: 100
            Brand: B, Month: APRIL, Monthly total: 120""",
            stringSummary);

    }
}

record GroupingKey(String brand, Month month) implements Comparable<GroupingKey>{

    @Override
    public int compareTo(GroupingKey other) {
        int monthComparison = month().compareTo(other.month());
        if (monthComparison != 0) {
            return monthComparison;
        }
        return brand().compareTo(other.brand());
    }

}

record MonthlyBrandPurchaseSummary(String brand, Month month, int totalPurchase) {
    @Override
    public String toString() {
        return "Brand: " + brand + ", Month: " + month + ", Monthly total: " + totalPurchase;
    }
}
