package com.demo.carsales.analytics.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.Objects;

@Entity
public class Car {

    private String model;

    private String brand;

    private int modelYear;

    private int price;

    @OneToOne(mappedBy = "car")
    private CarPurchasedEvent event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    public Car(String model, String brand, int modelYear, int price, @Nullable CarPurchasedEvent event) {
        this.model = model;
        this.brand = brand;
        this.modelYear = modelYear;
        this.price = price;
        this.event = event;
    }

    @PersistenceCreator
    public Car(int id, String model, String brand, int modelYear, int price, @Nullable CarPurchasedEvent event) {
        this.id = id;
        this.model = Objects.requireNonNull(model);
        this.brand = Objects.requireNonNull(brand);
        this.event = event;

        if (price < 0) {
            throw new IllegalArgumentException("Car price must not be a negative value");
        }

        if (modelYear < 0) {
            throw new IllegalArgumentException("Car year must not be a negative value");
        }

        this.modelYear = modelYear;
        this.price = price;
    }
    protected Car() {}

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getModelYear() {
        return modelYear;
    }

    public int getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public boolean isSold() {
        return (event != null);
    }
}
