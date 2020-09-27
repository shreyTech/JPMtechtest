package com.jpm.interview.dao;

import com.jpm.interview.domain.SaleNotification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SaleDAO {

    private List<SaleNotification> saleDB = new ArrayList<>();

    public void persistSale(SaleNotification saleNotification) {
        saleDB.add(saleNotification);
    }

    public Collection<SaleNotification> getAllSales() {
        return  Collections.unmodifiableCollection(saleDB);
    }

    public Integer getTotalNumberOfSales() {
        return saleDB.size();
    }

    public void addAdjustment(final String productName, Integer value) {

        saleDB.stream().filter(saleNotification -> {
            if (saleNotification.getProductName().equals(productName)) {
                return true;
            }
            return false;
        }).forEach(s -> s.setPrice(s.getPrice() + value));

    }

    public void subtractAdjustment(final String productName, Integer value) {

        saleDB.stream().filter(saleNotification -> {
            if (saleNotification.getProductName().equals(productName)) {
                if (saleNotification.getPrice() < value) {
                    throw new RuntimeException("adjusted value cannot be greater than value of the product");
                }
                return true;
            }
            return false;
        }).forEach(s -> s.setPrice(s.getPrice() - value));

    }

    public void multiplyAdjustment(final String productName, Integer value) {

        saleDB.stream().filter(saleNotification -> {
            if (saleNotification.getProductName().equals(productName)) {
                return true;
            }
            return false;
        }).forEach(s -> s.setPrice(s.getPrice() * value));
    }
}