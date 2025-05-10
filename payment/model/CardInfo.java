package com.myinappbilling.payment.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 * CardInfo stores credit/debit card-related information for payment processing.
 */
public class CardInfo implements Serializable {

    private String accountNumber;
    private String cardNumber;
    private int expirationMonth;
    private int expirationYear;
    private String cvv;

    public CardInfo() {
    }

    public CardInfo(String accountNumber, String cardNumber, int expirationMonth, int expirationYear, String cvv) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
    }

    // Getters and Setters

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    // Utility Methods

    public boolean isCardValid() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;

        return cardNumber != null && cardNumber.length() >= 12
                && expirationMonth >= 1 && expirationMonth <= 12
                && (expirationYear > currentYear || (expirationYear == currentYear && expirationMonth >= currentMonth))
                && cvv != null && cvv.length() == 3;
    }

    public String getMaskedCardNumber() {
        if (cardNumber != null && cardNumber.length() >= 4) {
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        }
        return "Invalid Card";
    }

    public boolean isSensitiveDataComplete() {
        return accountNumber != null && !accountNumber.isEmpty()
                && cardNumber != null && !cardNumber.isEmpty()
                && cvv != null && !cvv.isEmpty();
    }

    public String getFormattedExpiryDate() {
        return String.format("%02d/%d", expirationMonth, expirationYear);
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "accountNumber='" + accountNumber + '\'' +
                ", cardNumber='" + getMaskedCardNumber() + '\'' +
                ", expirationMonth=" + expirationMonth +
                ", expirationYear=" + expirationYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardInfo cardInfo = (CardInfo) o;
        return expirationMonth == cardInfo.expirationMonth &&
                expirationYear == cardInfo.expirationYear &&
                Objects.equals(accountNumber, cardInfo.accountNumber) &&
                Objects.equals(cardNumber, cardInfo.cardNumber) &&
                Objects.equals(cvv, cardInfo.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, cardNumber, expirationMonth, expirationYear, cvv);
    }
}