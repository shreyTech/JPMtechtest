package com.jpm.interview;

import com.jpm.interview.core.MessageProcessor;
import com.jpm.interview.core.ObjectFactory;
import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.SaleNotification;

//Entry point for processing sales message
public class SalesMessageListener {

    private ObjectFactory factory = ObjectFactory.getInstance();
    private boolean maxMessageCountReached = false;

    public void onMessage(SaleNotification message) {

        if (maxMessageCountReached) {
            throw new RuntimeException("Message cannot be processed as maximum capacity has reached");
        }

        MessageProcessor processor = factory.getMessageProcessor(message);
        processor.process(message);

        SaleDAO saleDAO = factory.getSaleDAO();
        if (saleDAO.getTotalNumberOfSales() == factory.getMaxMessageCount()) {
            System.out.println("Application is pausing as maximum messages have been processed");
            maxMessageCountReached = true;
        }
    }

}
