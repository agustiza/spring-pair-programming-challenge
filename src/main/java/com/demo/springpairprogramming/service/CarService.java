package com.demo.springpairprogramming.service;

import com.demo.springpairprogramming.model.Car;
import com.demo.springpairprogramming.model.CarPurchasedEvent;
import com.demo.springpairprogramming.model.CardNotAvailableException;
import com.demo.springpairprogramming.repository.CarRepository;

public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public void purchaseCar(CarPurchasedEvent event)
        throws CardNotAvailableException {

        Car car = repository.findOne(event.carId())
            .orElseThrow(CardNotAvailableException::new);

    }


}
