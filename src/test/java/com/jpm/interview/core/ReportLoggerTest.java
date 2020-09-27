package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.Adjustment;
import com.jpm.interview.domain.SaleNotification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportLoggerTest {

    @Mock
    private SaleDAO saleDAO;

    private ReportLogger reportLogger;

    @Before
    public void setUp() {
        reportLogger = new ReportLogger(saleDAO);
    }

    @Test
    public void should_generate_sales_report_correctly() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        String androidProductName = "Pixel 4XL";
        Integer quantity = 10;
        Integer price = 50;
        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);
        SaleNotification anAndroidSale = new SaleNotification(transactionId, androidProductName, quantity, price, null);

        List<SaleNotification> sales = Arrays.asList(anAppleSale, anAppleSale, anAppleSale, anAppleSale, anAppleSale, anAndroidSale, anAndroidSale, anAndroidSale);

        when(saleDAO.getAllSales()).thenReturn(Collections.unmodifiableList(sales));
        String report = reportLogger.generateSalesReport();

        Assert.assertTrue(report.contains("Product : Apple I10, totalSales : 50, totalPrice : 2500"));
        Assert.assertTrue(report.contains("Product : Pixel 4XL, totalSales : 30, totalPrice : 1500"));
    }


    @Test
    public void should_generate_adjustment_report_correctly() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        String androidProductName = "Pixel 4XL";
        Integer quantity = 10;
        Integer price = 50;
        Adjustment addAdjustment = new Adjustment();
        addAdjustment.setValue(10);
        addAdjustment.setOperation(Adjustment.Operation.ADD);
        Adjustment subtractAdjustment = new Adjustment();
        subtractAdjustment.setValue(10);
        subtractAdjustment.setOperation(Adjustment.Operation.SUBTRACT);

        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);
        SaleNotification anAndroidSale = new SaleNotification(transactionId, androidProductName, quantity, price, null);
        SaleNotification anAppleSaleWithAdjustment = new SaleNotification(transactionId, productName, quantity, price, addAdjustment);
        SaleNotification anAndroidSaleWithAdjustment = new SaleNotification(transactionId, androidProductName, quantity, price, subtractAdjustment);

        List<SaleNotification> sales = Arrays.asList(anAppleSale, anAppleSale, anAppleSaleWithAdjustment, anAppleSale, anAppleSaleWithAdjustment,
                anAndroidSale, anAndroidSale, anAndroidSaleWithAdjustment);

        when(saleDAO.getAllSales()).thenReturn(Collections.unmodifiableList(sales));
        String report = reportLogger.generateAdjustmentReport();

        Assert.assertTrue(report.contains("Adjustment : Product : Apple I10, Operation : ADD, Value : 10"));
        Assert.assertTrue(report.contains("Adjustment : Product : Apple I10, Operation : ADD, Value : 10"));
        Assert.assertTrue(report.contains("Adjustment : Product : Pixel 4XL, Operation : SUBTRACT, Value : 10"));
    }


}