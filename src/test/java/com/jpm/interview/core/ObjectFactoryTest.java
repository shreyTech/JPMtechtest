package com.jpm.interview.core;

import com.jpm.interview.domain.Adjustment;
import com.jpm.interview.domain.SaleNotification;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObjectFactoryTest {

    private ObjectFactory objectFactory = ObjectFactory.getInstance();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_provide_saleProcessor_when_adjustment_is_not_provided_inSaleNotification() {

        MessageProcessor processor = objectFactory.getMessageProcessor(new SaleNotification());
        Assert.assertTrue(processor instanceof SaleProcessor);
    }

    @Test
    public void should_provide_saleProcessor_when_saleNotification_has_adjustment() {

        SaleNotification saleNotification = new SaleNotification();
        saleNotification.setAdjustment(new Adjustment());

        MessageProcessor processor = objectFactory.getMessageProcessor(saleNotification);

        Assert.assertTrue(processor instanceof AdjustmentProcessor);
    }

    @Test
    public void should_throw_exception_when_saleNotificationIsNull() {

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("No message processor found for the message");

        objectFactory.getMessageProcessor(null);

    }

}