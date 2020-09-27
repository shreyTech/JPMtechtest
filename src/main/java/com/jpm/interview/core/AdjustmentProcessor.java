package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.SaleNotification;

public class AdjustmentProcessor extends SaleProcessor {

    AdjustmentProcessor(SaleDAO saleDAO, ReportLogger reportLogger, Integer detailReportInterval,
                        Integer maxMessages, SaleValidator validator) {
        super(saleDAO, reportLogger, detailReportInterval, maxMessages, validator);
    }

    protected void processBusinessLogic(SaleNotification message) {

        super.processBusinessLogic(message);

        switch (message.getAdjustment().getOperation()) {

            case ADD:
                saleDAO.addAdjustment(message.getProductName(), message.getAdjustment().getValue());
                break;
            case SUBTRACT:
                saleDAO.subtractAdjustment(message.getProductName(), message.getAdjustment().getValue());
                break;
            case MULTIPLY:
                saleDAO.multiplyAdjustment(message.getProductName(), message.getAdjustment().getValue());
                break;
            default:
                throw new RuntimeException("Operation Not supported "
                        + message.getAdjustment().getOperation());
        }
    }
}
