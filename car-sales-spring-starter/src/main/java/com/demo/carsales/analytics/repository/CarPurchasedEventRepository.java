package com.demo.carsales.analytics.repository;

import com.demo.carsales.analytics.model.CarPurchasedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface CarPurchasedEventRepository extends JpaRepository<CarPurchasedEvent, UUID> {

  Stream<CarPurchasedEvent> findCarPurchasedEventByDateBetween(
    ZonedDateTime start, ZonedDateTime end);

  List<CarPurchasedEvent> findByDateBetween(
    ZonedDateTime start, ZonedDateTime end);

}
