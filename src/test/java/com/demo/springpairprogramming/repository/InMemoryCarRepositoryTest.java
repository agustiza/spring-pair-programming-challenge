package com.demo.springpairprogramming.repository;

import com.demo.springpairprogramming.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryCarRepositoryTest {

    CarRepository repo;

    Car mockCar = new Car(1, "model", "brand", 2024, 1000);

    @BeforeEach
    void setupBefore() {
        repo = new InMemoryCarRepository();

    }

    @Test
    void insert() {
        repo.insert(mockCar);

        Car retrieved = repo.findOne(mockCar.getId()).get();

        assertEquals(retrieved, mockCar);
    }

    @Test
    void findOne() {
    }

    @Test
    void deleteCarById() {
    }

}