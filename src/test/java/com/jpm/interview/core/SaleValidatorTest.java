package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.Adjustment;
import com.jpm.interview.domain.SaleNotification;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SaleValidatorTest {

    private SaleValidator saleValidator = new SaleValidator();

    @Test
    public void should_returnValidationErrors_for_invalidSale() {

        SaleNotification sale = new SaleNotification();
        sale.setAdjustment(new Adjustment());
        List<String> errorMessages = saleValidator.validate(sale);

        Assert.assertEquals(6, errorMessages.size());

        Assert.assertEquals("Transaction Id must be provided in the message", errorMessages.get(0));
        Assert.assertEquals("Product Name must be provided in the message", errorMessages.get(1));
        Assert.assertEquals("Invalid price for the sale", errorMessages.get(2));
        Assert.assertEquals("Invalid quantity for the sale", errorMessages.get(3));
        Assert.assertEquals("No adjustment operation for the sale", errorMessages.get(4));
        Assert.assertEquals("Invalid adjustment value for the sale", errorMessages.get(5));

    }

    @Test
    public void should_returnValidationError_for_nullSale() {

        List<String> errorMessages = saleValidator.validate(null);

        Assert.assertEquals(1, errorMessages.size());
        Assert.assertEquals("Message should not be null", errorMessages.get(0));

    }

    @Test
    public void should_notReturnError_for_validSale() {

        Long transactionId = 1733l;
        String productName = "Apple I10";
        Integer quantity = 10;
        Integer price = 50;
        SaleNotification anAppleSale = new SaleNotification(transactionId, productName, quantity, price, null);

        List<String> errorMessages = saleValidator.validate(anAppleSale);

        Assert.assertEquals(0, errorMessages.size());

    }

}