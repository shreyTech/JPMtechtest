package com.jpm.interview.dao;

import com.jpm.interview.domain.SaleNotification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SaleDAOTest {

    private SaleDAO saleDAO;
    private SaleNotification aAppleSale;
    private SaleNotification anotherAppleSale;
    private SaleNotification anAndroidSale;

    @Before
    public void setup() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        String androidProductName = "Google Pixel 4";

        Integer quantity = 10;
        Integer price = 50;
        saleDAO = new SaleDAO();
        aAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);
        anotherAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);
        anAndroidSale = new SaleNotification(transactionId, androidProductName, quantity, price, null);
    }

    @Test
    public void should_persist_sale_correctly() {

        saleDAO.persistSale(aAppleSale);

        Assert.assertEquals(new Integer(1), saleDAO.getTotalNumberOfSales());
        Assert.assertEquals(aAppleSale, saleDAO.getAllSales().toArray()[0]);

    }

    @Test
    public void should_adjust_correctly_when_addOperationIsPerformed() {

        saleDAO.persistSale(aAppleSale);
        saleDAO.persistSale(anotherAppleSale);
        saleDAO.persistSale(anAndroidSale);

        saleDAO.addAdjustment(aAppleSale.getProductName(), 20);

        Assert.assertEquals(new Integer(3), saleDAO.getTotalNumberOfSales());
        SaleNotification firstSale = (SaleNotification) saleDAO.getAllSales().toArray()[0];
        SaleNotification secondSale = (SaleNotification) saleDAO.getAllSales().toArray()[1];
        SaleNotification thirdSale = (SaleNotification) saleDAO.getAllSales().toArray()[2];

        Assert.assertEquals(new Integer(70), firstSale.getPrice());
        Assert.assertEquals(new Integer(70), secondSale.getPrice());
        Assert.assertEquals(new Integer(50), thirdSale.getPrice());
    }

    @Test
    public void should_adjust_correctly_when_subtractOperationIsPerformed() {

        saleDAO.persistSale(aAppleSale);
        saleDAO.persistSale(anotherAppleSale);
        saleDAO.persistSale(anAndroidSale);

        saleDAO.subtractAdjustment(aAppleSale.getProductName(), 20);

        Assert.assertEquals(new Integer(3), saleDAO.getTotalNumberOfSales());
        SaleNotification firstSale = (SaleNotification) saleDAO.getAllSales().toArray()[0];
        SaleNotification secondSale = (SaleNotification) saleDAO.getAllSales().toArray()[1];
        SaleNotification thirdSale = (SaleNotification) saleDAO.getAllSales().toArray()[2];

        Assert.assertEquals(new Integer(30), firstSale.getPrice());
        Assert.assertEquals(new Integer(30), secondSale.getPrice());
        Assert.assertEquals(new Integer(50), thirdSale.getPrice());

    }

    @Test
    public void should_adjust_correctly_when_multiplyOperationIsPerformed() {

        saleDAO.persistSale(aAppleSale);
        saleDAO.persistSale(anotherAppleSale);
        saleDAO.persistSale(anAndroidSale);

        saleDAO.multiplyAdjustment(anAndroidSale.getProductName(), 2);

        Assert.assertEquals(new Integer(3), saleDAO.getTotalNumberOfSales());
        SaleNotification firstSale = (SaleNotification) saleDAO.getAllSales().toArray()[0];
        SaleNotification secondSale = (SaleNotification) saleDAO.getAllSales().toArray()[1];
        SaleNotification thirdSale = (SaleNotification) saleDAO.getAllSales().toArray()[2];

        Assert.assertEquals(new Integer(50), firstSale.getPrice());
        Assert.assertEquals(new Integer(50), secondSale.getPrice());
        Assert.assertEquals(new Integer(100), thirdSale.getPrice());

    }
}