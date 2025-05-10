package com.myinappbilling.creditcardreceipt.model;

import java.util.Date;
import java.util.Objects;

/**
 * Model class representing transaction information.
 */
public class TransactionInfo {

    private String transactionId;
    private String cardLastFourDigits;
    private Date transactionDate;
    private double transactionAmount;
    private String merchantName;
    private String transactionType;
    private String currency;
    private boolean isInternational;
    private String authorizationCode;

    public TransactionInfo(String transactionId, String cardLastFourDigits, Date transactionDate, double transactionAmount,
                            String merchantName, String transactionType, String currency, boolean isInternational, String authorizationCode) {
        this.transactionId = transactionId;
        this.cardLastFourDigits = cardLastFourDigits;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.merchantName = merchantName;
        this.transactionType = transactionType;
        this.currency = currency;
        this.isInternational = isInternational;
        this.authorizationCode = authorizationCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardLastFourDigits() {
        return cardLastFourDigits;
    }

    public void setCardLastFourDigits(String cardLastFourDigits) {
        this.cardLastFourDigits = cardLastFourDigits;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public void setInternational(boolean international) {
        isInternational = international;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public boolean isValidTransaction() {
        return transactionId != null && !transactionId.isEmpty()
                && cardLastFourDigits != null && cardLastFourDigits.length() == 4
                && transactionDate != null
                && transactionAmount >= 0
                && merchantName != null && !merchantName.isEmpty()
                && transactionType != null && !transactionType.isEmpty()
                && currency != null && !currency.isEmpty()
                && authorizationCode != null && !authorizationCode.isEmpty();
    }

    public boolean isRefund() {
        return "REFUND".equalsIgnoreCase(transactionType);
    }

    public boolean isLargeTransaction(double threshold) {
        return transactionAmount > threshold;
    }

    @Override
    public String toString() {
        return "TransactionInfo{" +
                "transactionId='" + transactionId + '\'' +
                ", cardLastFourDigits='" + cardLastFourDigits + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                ", merchantName='" + merchantName + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", currency='" + currency + '\'' +
                ", isInternational=" + isInternational +
                ", authorizationCode='" + authorizationCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInfo that = (TransactionInfo) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}
