package com.jpm.interview.integration;

import com.jpm.interview.SalesMessageListener;
import com.jpm.interview.core.ObjectFactory;
import com.jpm.interview.domain.SaleNotification;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SalesMessageListenerStressTest {

    private SalesMessageListener salesMessageListener = new SalesMessageListener();
    private ObjectFactory factory = ObjectFactory.getInstance();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_throwException_afterMaximum_messages_are_processed() {

        Long transactionId = 1733l;
        String productName = "Samsung Galaxy X";
        Integer quantity = 10;
        Integer price = 50;

        SaleNotification aSamsungSale = new SaleNotification(transactionId, productName, quantity, price, null);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Message cannot be processed as maximum capacity has reached");

        for (int i = 0; i<=51; i++) {
            salesMessageListener.onMessage(aSamsungSale);
        }

        Assert.assertEquals(new Integer(50), factory.getSaleDAO().getTotalNumberOfSales());
    }

}
