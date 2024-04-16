package com.demo.carsales.analytics.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class CarPurchasedEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  private ZonedDateTime date;

  private double purchasePrice;

  @OneToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, updatable = false)
  private Car car;

  @PersistenceCreator
  public CarPurchasedEvent(ZonedDateTime date, double purchasePrice, Car car) {
    this.date = date;
    this.purchasePrice = purchasePrice;
    this.car = car;
  }

  protected CarPurchasedEvent() {}

  public UUID getId() {
    return id;
  }

  public ZonedDateTime getDate() {
    return date;
  }

  public double getPurchasePrice() {
    return purchasePrice;
  }

  public Car getCar() {
    return car;
  }
}
