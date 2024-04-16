package com.demo.carsales.analytics.service;

sealed public interface OrderResult {}
record OrderApproved(PurchaseOrder order) implements OrderResult {}
record OrderRejected(PurchaseOrder order, String reason) implements OrderResult {}
