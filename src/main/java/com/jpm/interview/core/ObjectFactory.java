package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.SaleNotification;

//Singleton Factory class to create objects, a dependency injection framework like Spring can be used instead.
public final class ObjectFactory {

    private static final ObjectFactory processorFactory = new ObjectFactory();

    private SaleValidator saleValidator;
    private MessageProcessor saleProcessor;
    private MessageProcessor adjustmentProcessor;
    private SaleDAO saleDAO;
    private ReportLogger reportLogger;
    private Integer detailReportInterval = 10;
    private Integer maxMessageCount = 50;

    private ObjectFactory() {

        this.saleDAO = new SaleDAO();
        this.reportLogger = new ReportLogger(saleDAO);
        this.saleValidator = new SaleValidator();
        this.saleProcessor = new SaleProcessor(saleDAO, reportLogger, detailReportInterval, maxMessageCount, saleValidator);
        this.adjustmentProcessor = new AdjustmentProcessor(saleDAO, reportLogger, detailReportInterval, maxMessageCount,
            saleValidator);
    }

    public static ObjectFactory getInstance() {
        return processorFactory;
    }

    public MessageProcessor getMessageProcessor(SaleNotification message) {

        if (message == null) {
            throw new RuntimeException("No message processor found for the message");
        }

        if (message.getAdjustment() == null) {
            return saleProcessor;
        } else {
            return adjustmentProcessor;
        }
    }

    public Integer getMaxMessageCount() {
        return maxMessageCount;
    }

    public SaleDAO getSaleDAO() {
        return saleDAO;
    }

    public ReportLogger getReportLogger() {
        return reportLogger;
    }
}
