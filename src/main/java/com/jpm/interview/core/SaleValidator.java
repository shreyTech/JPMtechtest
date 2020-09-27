package com.jpm.interview.core;

import com.jpm.interview.domain.SaleNotification;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SaleValidator {

    public List<String> validate(SaleNotification saleNotification) {

        List<String> invalidMessageList = new ArrayList<>();

        if (saleNotification == null) {
            invalidMessageList.add("Message should not be null");
        } else {

            if (saleNotification.getTransactionId() == null) {
                invalidMessageList.add("Transaction Id must be provided in the message");
            }
            if (StringUtils.isBlank(saleNotification.getProductName())) {
                invalidMessageList.add("Product Name must be provided in the message");
            }
            if (saleNotification.getPrice() == null || saleNotification.getPrice() <= 0) {
                invalidMessageList.add("Invalid price for the sale");
            }
            if (saleNotification.getQuantity() == null || saleNotification.getQuantity() <= 0) {
                invalidMessageList.add("Invalid quantity for the sale");
            }
            if (saleNotification.getAdjustment() != null) {

                if (saleNotification.getAdjustment().getOperation() == null) {
                    invalidMessageList.add("No adjustment operation for the sale");
                }
                if (saleNotification.getAdjustment().getValue() == null ||
                        saleNotification.getAdjustment().getValue() <= 0) {
                    invalidMessageList.add("Invalid adjustment value for the sale");
                }
            }

        }

        return invalidMessageList;
    }
}
