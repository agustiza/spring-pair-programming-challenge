package com.demo.springpairprogramming.service;

import com.demo.springpairprogramming.model.CarPurchasedEvent;
import com.demo.springpairprogramming.repository.CarRepository;

import java.time.Month;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class SalesSummarizer {

    private CarRepository repository;

    public SalesSummarizer(CarRepository repository) {
        this.repository = repository;
    }

    public List<MonthlyCarSaleByBrandRow> calcSalesByMonthAndBrand(List<CarPurchasedEvent> carEvents) {
        return carEvents.stream()
            .collect(
                groupingBy(event -> new GroupingKey(
                    repository.findOne(event.carId()).get().getBrand(),
                        event.date().getMonth()),
                    TreeMap::new,
                    Collectors.summingInt(CarPurchasedEvent::purchasePrice)))
            .entrySet().stream()
            .map(entry -> new MonthlyCarSaleByBrandRow(
                entry.getKey().brand(),
                entry.getKey().month(),
                entry.getValue()))
            .toList();
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
