package com.myinappbilling.payment.validator;

import com.myinappbilling.payment.model.CardInfo;
import com.myinappbilling.payment.model.PersonalInfo;

import java.util.regex.Pattern;
import java.time.YearMonth;

/**
 * PaymentValidator provides utility methods for validating payment and personal information.
 */
public class PaymentValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.+]+@[\\w-]+\\.[a-z]{2,4}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9. ()-]{7,25}$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{12,19}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^[0-9]{3,4}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z '-]{2,50}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && CARD_NUMBER_PATTERN.matcher(cardNumber).matches();
    }

    public static boolean isValidCvv(String cvv) {
        return cvv != null && CVV_PATTERN.matcher(cvv).matches();
    }

    public static boolean isValidExpiryDate(int month, int year) {
        try {
            YearMonth expiry = YearMonth.of(year, month);
            return !expiry.isBefore(YearMonth.now());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPostalCode(String postalCode) {
        return postalCode != null && POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean validateCardInfo(CardInfo cardInfo) {
        return cardInfo != null &&
                isValidCardNumber(cardInfo.getCardNumber()) &&
                isValidExpiryDate(cardInfo.getExpirationMonth(), cardInfo.getExpirationYear()) &&
                isValidCvv(cardInfo.getCvv());
    }

    public static boolean validatePersonalInfo(PersonalInfo info) {
        return info != null &&
                isValidName(info.getFirstName()) &&
                isValidName(info.getLastName()) &&
                isValidEmail(info.getEmail()) &&
                isValidPhoneNumber(info.getCellphoneNumber()) &&
                isNotEmpty(info.getIdentityCardNumber()) &&
                isNotEmpty(info.getOccupation()) &&
                isNotEmpty(info.getCity()) &&
                isValidPostalCode(info.getPostalCode()) &&
                isNotEmpty(info.getCountry()) &&
                isNotEmpty(info.getAddress()) &&
                isNotEmpty(info.getProvince());
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
