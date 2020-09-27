package com.jpm.interview.integration;

import com.jpm.interview.SalesMessageListener;
import com.jpm.interview.core.ObjectFactory;
import com.jpm.interview.domain.Adjustment;
import com.jpm.interview.domain.SaleNotification;
import org.junit.Assert;
import org.junit.Test;

public class SalesMessageListenerTest {

    private SalesMessageListener salesMessageListener = new SalesMessageListener();
    private ObjectFactory factory = ObjectFactory.getInstance();

    @Test
    public void should_process_valid_saleNotification_successfully() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);

        salesMessageListener.onMessage(anAppleSale);

        SaleNotification saleFromDB = factory.getSaleDAO().getAllSales().stream()
                .filter(saleNotification -> saleNotification.getProductName().equals(productName)).findFirst().get();

        Assert.assertEquals(anAppleSale, saleFromDB);

    }

    @Test
    public void should_process_valid_saleNotification_withAddAdjustment_successfully() {

        Long transactionId = 1733l;
        String productName = "Samsung Galaxy 8";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment adjustment = new Adjustment();
        adjustment.setOperation(Adjustment.Operation.ADD);
        adjustment.setValue(10);

        SaleNotification aSamsungSale = new SaleNotification(transactionId, productName, quantity, price, adjustment);

        salesMessageListener.onMessage(aSamsungSale);

        SaleNotification saleFromDB = factory.getSaleDAO().getAllSales().stream()
                .filter(saleNotification -> saleNotification.getProductName().equals(productName)).findFirst().get();

        Assert.assertEquals(new Integer(60), saleFromDB.getPrice());

    }

    @Test
    public void should_process_valid_saleNotification_withSubtractAdjustment_successfully() {

        Long transactionId = 1733l;
        String productName = "Samsung Galaxy 9";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment adjustment = new Adjustment();
        adjustment.setOperation(Adjustment.Operation.SUBTRACT);
        adjustment.setValue(10);

        SaleNotification aSamsungSale = new SaleNotification(transactionId, productName, quantity, price, adjustment);

        salesMessageListener.onMessage(aSamsungSale);

        SaleNotification saleFromDB = factory.getSaleDAO().getAllSales().stream()
                .filter(saleNotification -> saleNotification.getProductName().equals(productName)).findFirst().get();

        Assert.assertEquals(new Integer(40), saleFromDB.getPrice());

    }

    @Test
    public void should_process_valid_saleNotification_withMultiplyAdjustment_successfully() {

        Long transactionId = 1733l;
        String productName = "Samsung Galaxy 10";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment adjustment = new Adjustment();
        adjustment.setOperation(Adjustment.Operation.MULTIPLY);
        adjustment.setValue(2);

        SaleNotification aSamsungSale = new SaleNotification(transactionId, productName, quantity, price, adjustment);

        salesMessageListener.onMessage(aSamsungSale);

        SaleNotification saleFromDB = factory.getSaleDAO().getAllSales().stream()
                .filter(saleNotification -> saleNotification.getProductName().equals(productName)).findFirst().get();

        Assert.assertEquals(new Integer(100), saleFromDB.getPrice());

    }

}