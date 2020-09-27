package com.jpm.interview.domain;

public class SaleNotification {

    public SaleNotification() {
    }

    public SaleNotification(Long transactionId, String productName, Integer quantity,
                            Integer price, Adjustment adjustment) {

        this.transactionId = transactionId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.adjustment = adjustment;
    }

    private Long transactionId;

    private String productName;

    private Integer quantity;

    private Integer price;

    private Adjustment adjustment;

    public Integer getTotalSalePrice() {
        return quantity * price;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Adjustment getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Adjustment adjustment) {
        this.adjustment = adjustment;
    }
}
