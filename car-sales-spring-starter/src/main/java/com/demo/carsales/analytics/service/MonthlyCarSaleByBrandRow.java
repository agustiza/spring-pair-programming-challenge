package com.demo.carsales.analytics.service;

import java.time.YearMonth;

public record MonthlyCarSaleByBrandRow(String brand, YearMonth yearMonth, Double totalPurchase) {
    @Override
    public String toString() {
        return brand + ", " + yearMonth + ", " + totalPurchase;
    }
}
