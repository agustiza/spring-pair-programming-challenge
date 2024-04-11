package com.demo.springpairprogramming.model;

import java.time.ZonedDateTime;

public record CarPurchasedEvent(ZonedDateTime date, int purchasePrice, int carId) {
}
