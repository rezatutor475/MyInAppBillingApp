package com.myinappbilling.financial.model;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a financial transaction in the system.
 */
public class Transaction {

    private String transactionId;
    private String userId;
    private String productId;
    private double amount;
    private String currency;
    private Date transactionDate;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String paymentMethod;
    private String description;
    private boolean isRefundable;
    private String refundId;
    private Date lastUpdated;

    public Transaction() {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionDate = new Date();
        this.transactionStatus = TransactionStatus.PENDING;
        this.lastUpdated = new Date();
    }

    public Transaction(String userId, String productId, double amount, String currency,
                       TransactionType transactionType, String paymentMethod, String description, boolean isRefundable) {
        this();
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.isRefundable = isRefundable;
    }

    // Getters and setters

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        updateTimestamp();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
        updateTimestamp();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        updateTimestamp();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        updateTimestamp();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
        updateTimestamp();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        updateTimestamp();
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
        updateTimestamp();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
        updateTimestamp();
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
        updateTimestamp();
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    private void updateTimestamp() {
        this.lastUpdated = new Date();
    }
}

// Supporting Enums

enum TransactionType {
    PURCHASE,
    REFUND,
    SUBSCRIPTION,
    DONATION,
    TRANSFER,
    OTHER
}

enum TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED,
    REFUNDED,
    IN_REVIEW
}
