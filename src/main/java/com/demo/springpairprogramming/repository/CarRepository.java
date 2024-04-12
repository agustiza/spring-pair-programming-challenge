package com.demo.springpairprogramming.repository;

import com.demo.springpairprogramming.model.Car;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CarRepository {
    Optional<Car> findOne(int id);

    void deleteCarById(int id);

    void insert(Car newCar);
}
