package com.demo.carsales.analytics.service;

import com.demo.carsales.analytics.model.CarPurchasedEvent;
import com.demo.carsales.analytics.repository.CarPurchasedEventRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class SalesSummarizer {

    private final CarPurchasedEventRepository carPurchasedEventRepository;

    public SalesSummarizer(CarPurchasedEventRepository carPurchasedEventRepository) {
        this.carPurchasedEventRepository = carPurchasedEventRepository;
    }

    /**
     * Sums the car sales total revenue grouped by brand and yearMonth
     *
     * @param start start date inclusive.
     * @param end end date exclusive.
     * @return An ordered list of
     */
    @Transactional(readOnly = true)
    public List<MonthlyCarSaleByBrandRow> sumTotalSalesByMonthAndBrand(ZonedDateTime start, ZonedDateTime end) {
        try (var events = carPurchasedEventRepository
          .findCarPurchasedEventByDateBetween(start, end)) {
            return events.collect(
                groupingBy(event -> new GroupingKey(
                    event.getCar().getBrand(),
                    YearMonth.of(
                      event.getDate().getYear(),
                      event.getDate().getMonth())
                  ),
                  TreeMap::new,
                  Collectors.summingDouble(CarPurchasedEvent::getPurchasePrice)))
              .entrySet().stream()
              .map(entry -> new MonthlyCarSaleByBrandRow(
                entry.getKey().brand(),
                entry.getKey().yearMonth(),
                entry.getValue()))
              .toList();
        }
    }
}

record GroupingKey(String brand, YearMonth yearMonth) implements Comparable<GroupingKey>{

    @Override
    public int compareTo(GroupingKey other) {
        int monthComparison = yearMonth().compareTo(other.yearMonth());
        if (monthComparison != 0) {
            return monthComparison;
        }
        return brand().compareTo(other.brand());
    }

}
