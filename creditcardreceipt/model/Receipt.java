package com.myinappbilling.creditcardreceipt.model;

import java.util.List;
import java.util.Date;
import java.util.Objects;
import java.text.SimpleDateFormat;

/**
 * Model class representing a credit card receipt.
 */
public class Receipt {

    private String receiptId;
    private Date transactionDate;
    private String merchantName;
    private List<ReceiptItem> items;
    private double totalAmount;
    private TransactionInfo transactionInfo;
    private String currency;
    private String paymentMethod;

    public Receipt(String receiptId, Date transactionDate, String merchantName, List<ReceiptItem> items, double totalAmount, TransactionInfo transactionInfo, String currency, String paymentMethod) {
        this.receiptId = receiptId;
        this.transactionDate = transactionDate;
        this.merchantName = merchantName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.transactionInfo = transactionInfo;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public boolean isValidReceipt() {
        return receiptId != null && !receiptId.isEmpty()
                && merchantName != null && !merchantName.isEmpty()
                && transactionDate != null
                && totalAmount >= 0
                && currency != null && !currency.isEmpty()
                && paymentMethod != null && !paymentMethod.isEmpty();
    }

    public String getFormattedTransactionDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return transactionDate != null ? formatter.format(transactionDate) : "N/A";
    }

    public double calculateTax(double taxRate) {
        return totalAmount * taxRate / 100;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", transactionDate=" + transactionDate +
                ", merchantName='" + merchantName + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", transactionInfo=" + transactionInfo +
                ", currency='" + currency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(receiptId, receipt.receiptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptId);
    }
}
