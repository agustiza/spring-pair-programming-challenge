package com.demo.springpairprogramming.repository;

import com.demo.springpairprogramming.model.Car;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCarRepository implements CarRepository {

    private Map<Integer, Car> map = new HashMap();

    @Override
    public Optional<Car> findOne(int id) {
        if (map.containsKey(id)) {
            return Optional.of(map.get(id));
        } else {
            return Optional.empty();
        }

    }

    @Override
    public void deleteCarById(int id) {
        map.remove(id);
    }

    @Override
    public void insert(Car newCar) {
        map.put(newCar.getId(), newCar);
    }
}
