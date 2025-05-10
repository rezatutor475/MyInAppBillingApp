package com.myinappbilling.payment.service;

import com.myinappbilling.payment.model.PaymentDetails;
import com.myinappbilling.payment.validator.CardValidator;
import com.myinappbilling.payment.validator.IdentityValidator;
import com.myinappbilling.payment.validator.PaymentValidator;

import java.util.Optional;

/**
 * ValidationService provides centralized validation logic
 * for various aspects of payment processing.
 */
public class ValidationService {

    /**
     * Validates the entire payment details object.
     *
     * @param details the PaymentDetails object to validate
     * @return true if valid, false otherwise
     */
    public boolean validateAll(PaymentDetails details) {
        return validatePersonalInfo(details) &&
               validateCardInfo(details) &&
               validatePaymentData(details);
    }

    /**
     * Validates personal information fields.
     *
     * @param details the PaymentDetails object containing personal info
     * @return true if personal info is valid
     */
    public boolean validatePersonalInfo(PaymentDetails details) {
        return Optional.ofNullable(details.getPersonalInfo())
                .map(IdentityValidator::validatePersonalInfo)
                .orElse(false);
    }

    /**
     * Validates card-related information.
     *
     * @param details the PaymentDetails object containing card info
     * @return true if card info is valid
     */
    public boolean validateCardInfo(PaymentDetails details) {
        return Optional.ofNullable(details.getCardInfo())
                .map(card -> CardValidator.validateCardInfo(
                        card.getCardNumber(),
                        card.getExpirationMonth(),
                        card.getExpirationYear(),
                        card.getCvv()
                ))
                .orElse(false);
    }

    /**
     * Validates payment-specific rules.
     *
     * @param details the PaymentDetails object
     * @return true if payment rules are met
     */
    public boolean validatePaymentData(PaymentDetails details) {
        return PaymentValidator.validatePaymentDetails(details);
    }

    /**
     * Checks if the provided email format is valid.
     *
     * @param email the email string to validate
     * @return true if the email is in valid format
     */
    public boolean isEmailValid(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    /**
     * Checks if the cellphone number is valid based on a simple pattern.
     *
     * @param cellphoneNumber the phone number to validate
     * @return true if valid
     */
    public boolean isCellphoneValid(String cellphoneNumber) {
        return cellphoneNumber != null && cellphoneNumber.matches("^\\+?[0-9]{7,15}$");
    }

    /**
     * Checks if the postal code is valid.
     *
     * @param postalCode the postal code to validate
     * @return true if valid
     */
    public boolean isPostalCodeValid(String postalCode) {
        return postalCode != null && postalCode.matches("^[A-Za-z0-9\\s-]{3,10}$");
    }

    /**
     * Checks if the province is provided.
     *
     * @param province the province name
     * @return true if not empty
     */
    public boolean isProvinceValid(String province) {
        return province != null && !province.trim().isEmpty();
    }

    /**
     * Validates CVV format (3 or 4 digits).
     *
     * @param cvv the CVV code
     * @return true if format is correct
     */
    public boolean isCvvValid(String cvv) {
        return cvv != null && cvv.matches("^[0-9]{3,4}$");
    }

    /**
     * Validates name field (first or last).
     *
     * @param name the name string
     * @return true if not empty and alphabetic
     */
    public boolean isNameValid(String name) {
        return name != null && name.matches("^[A-Za-z\\s'-]{2,50}$");
    }

    /**
     * Validates address field.
     *
     * @param address the address string
     * @return true if not null and length within limit
     */
    public boolean isAddressValid(String address) {
        return address != null && address.length() <= 100;
    }
} 
