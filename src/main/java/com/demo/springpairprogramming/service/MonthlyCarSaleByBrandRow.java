package com.demo.springpairprogramming.service;

import java.time.Month;

public record MonthlyCarSaleByBrandRow(String brand, Month month, int totalPurchase) {
    @Override
    public String toString() {
        return brand + ", " + month + ", " + totalPurchase;
    }
}
