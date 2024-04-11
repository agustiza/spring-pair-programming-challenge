package com.demo.springpairprogramming.model;

import java.util.Objects;

public class Car {

    private String model;

    private String brand;

    private int year;

    private int price;

    private int id;

    public Car(int id, String model, String brand, int year, int price) {
        this.id = id;
        this.model = Objects.requireNonNull(model);
        this.brand = Objects.requireNonNull(brand);

        if (price < 0) {
            throw new IllegalArgumentException("Car price must not be a negative value");
        }

        if (year < 0) {
            throw new IllegalArgumentException("Car year must not be a negative value");
        }

        this.year = year;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
}
