package com.myinappbilling.payment.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * PaymentDetails encapsulates all the necessary details required to process a payment.
 */
public class PaymentDetails implements Serializable {

    private String paymentId;
    private PersonalInfo personalInfo;
    private CardInfo cardInfo;
    private String currency;
    private String paymentMethod;
    private boolean isRecurring;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaymentDetails() {
        this.paymentId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public PaymentDetails(PersonalInfo personalInfo, CardInfo cardInfo, String currency, String paymentMethod, boolean isRecurring) {
        this.paymentId = UUID.randomUUID().toString();
        this.personalInfo = personalInfo;
        this.cardInfo = cardInfo;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.isRecurring = isRecurring;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        updateTimestamp();
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
        updateTimestamp();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        updateTimestamp();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        updateTimestamp();
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Validates if the payment details are complete.
     * @return true if all required fields are populated; false otherwise.
     */
    public boolean isValid() {
        return personalInfo != null && personalInfo.isValid()
                && cardInfo != null && cardInfo.isValid()
                && currency != null && !currency.isEmpty()
                && paymentMethod != null && !paymentMethod.isEmpty();
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "paymentId='" + paymentId + '\'' +
                ", personalInfo=" + personalInfo +
                ", cardInfo=" + cardInfo +
                ", currency='" + currency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", isRecurring=" + isRecurring +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDetails that = (PaymentDetails) o;
        return isRecurring == that.isRecurring &&
                Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(personalInfo, that.personalInfo) &&
                Objects.equals(cardInfo, that.cardInfo) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, personalInfo, cardInfo, currency, paymentMethod, isRecurring);
    }
}
