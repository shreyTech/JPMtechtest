package com.jpm.interview.core;

import com.jpm.interview.domain.SaleNotification;

public interface MessageProcessor {

    void process(SaleNotification message);
}
