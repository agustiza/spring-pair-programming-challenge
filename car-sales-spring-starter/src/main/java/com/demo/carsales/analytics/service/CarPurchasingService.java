package com.demo.carsales.analytics.service;

import com.demo.carsales.analytics.model.Car;
import com.demo.carsales.analytics.model.CarPurchasedEvent;
import com.demo.carsales.analytics.model.CarNotAvailableException;
import com.demo.carsales.analytics.repository.CarPurchasedEventRepository;
import com.demo.carsales.analytics.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Order;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class CarPurchasingService {

    private final CarRepository repository;

    private final CarPurchasedEventRepository events;

    public CarPurchasingService(
      CarRepository repository,
      CarPurchasedEventRepository events) {
        this.repository = repository;
        this.events = events;
    }

    /**
     * Process and validates car orders according to business logic.
     * @param carOrder a car order.
     * @return an order result.
     */
    public OrderResult purchaseCar(PurchaseOrder carOrder) {
        Car car = repository.findById(carOrder.carId())
          .orElseThrow(() -> new EntityNotFoundException("Car does not exist"));

        // Some business logic
        if (car.isSold()) {
            // Using Result type with records, could be handled in many different ways.
            return new OrderRejected(carOrder, "Car is unavailable");
        }

        if (carOrder.price() > car.getPrice()) {
            CarPurchasedEvent ev = new CarPurchasedEvent(ZonedDateTime.now(), carOrder.price(), car);
            events.save(ev);
            return new OrderApproved(carOrder);
        } else {
            return new OrderRejected(carOrder, "Offer must at least match price.");
        }
    }

}
