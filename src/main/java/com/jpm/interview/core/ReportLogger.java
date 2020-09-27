package com.jpm.interview.core;

import com.jpm.interview.dao.SaleDAO;
import com.jpm.interview.domain.SaleNotification;

import java.util.*;

public class ReportLogger {

    private SaleDAO saleDAO;
    private String lineSeparator = System.lineSeparator();

    public ReportLogger(SaleDAO saleDAO) {
        this.saleDAO = saleDAO;
    }

    public String generateSalesReport() {

        Map<String, List<Integer>> saleReport = new HashMap<>();

        saleDAO.getAllSales().stream().forEach(saleNotification -> {

            if (saleReport.containsKey(saleNotification.getProductName())) {
                List<Integer> saleData = saleReport.get(saleNotification.getProductName());
                Integer totalSales = saleData.get(0) + saleNotification.getQuantity();
                Integer totalPrice = saleData.get(1) + saleNotification.getTotalSalePrice();
                saleData.set(0, totalSales);
                saleData.set(1, totalPrice);
                saleReport.put(saleNotification.getProductName(), saleData);
            } else {
                List<Integer> saleData = Arrays.asList(saleNotification.getQuantity(), saleNotification.getTotalSalePrice());
                saleReport.put(saleNotification.getProductName(), saleData);
            }

        });

        StringBuilder report = new StringBuilder("##############################SALE REPORT############################").append(lineSeparator);
        saleReport.entrySet().stream().forEach(entry ->
            report.append("Product : ").append(entry.getKey())
                  .append(", totalSales : ").append(entry.getValue().get(0))
                  .append(", totalPrice : ").append(entry.getValue().get(1))
                  .append(lineSeparator));
        report.append("##############################SALE REPORT############################").append(lineSeparator);

        return report.toString();

    }

    public String generateAdjustmentReport() {

        Collection<SaleNotification> sales = saleDAO.getAllSales();
        StringBuilder report = new StringBuilder("##############################ADJUSTMENT REPORT############################").append(lineSeparator);
        sales.stream().filter(saleNotification -> {
            if (saleNotification.getAdjustment() != null) {
                return true;
            }
            return false;
        }).forEach(s -> report.append("Adjustment : Product : ").append(s.getProductName())
                .append(", Operation : ").append(s.getAdjustment().getOperation())
                .append(", Value : ").append(s.getAdjustment().getValue())
                .append(lineSeparator));

        report.append("##############################ADJUSTMENT REPORT############################").append(lineSeparator);

        return report.toString();
    }
}
