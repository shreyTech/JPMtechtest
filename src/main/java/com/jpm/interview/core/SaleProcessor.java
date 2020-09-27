package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.SaleNotification;

import java.util.List;

class SaleProcessor implements MessageProcessor {

    private String lineSeparator = System.lineSeparator();
    private SaleValidator validator;
    private Integer saleReportInterval;
    private Integer adjustmentReportInterval;
    private ReportLogger reportLogger;
    protected SaleDAO saleDAO;

    SaleProcessor(SaleDAO saleDAO, ReportLogger reportLogger, Integer saleReportInterval,
                  Integer maxMessages, SaleValidator validator) {
        this.saleDAO = saleDAO;
        this.reportLogger = reportLogger;
        this.saleReportInterval = saleReportInterval;
        this.adjustmentReportInterval = maxMessages;
        this.validator = validator;
    }

    public final void process(SaleNotification message) {

        List<String> violations = validate(message);
        if (violations.size() != 0) {
            violations.stream().forEach(s -> System.out.println(s + lineSeparator));
            throw new RuntimeException("Invalid sale message, missing mandatory parameters");
        }

        processBusinessLogic(message);
        postProcess(message);
    }

    protected List<String> validate(SaleNotification message) {
        return validator.validate(message);
    }

    protected void processBusinessLogic(SaleNotification message) {
        saleDAO.persistSale(message);
    }

    protected void postProcess(SaleNotification message) {

        Integer totalSales = saleDAO.getTotalNumberOfSales();
        if (totalSales%saleReportInterval == 0) {
            System.out.println(reportLogger.generateSalesReport());
        }
        if (totalSales% adjustmentReportInterval == 0) {
            System.out.println(reportLogger.generateAdjustmentReport());
        }

    }

}
