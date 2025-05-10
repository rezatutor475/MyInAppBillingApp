package com.myinappbilling.payment.validator;

import java.util.regex.Pattern;
import java.time.YearMonth;

/**
 * CardValidator provides utility methods for validating credit/debit card information
 * and associated billing details.
 */
public class CardValidator {

    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^\\d{13,19}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3,4}$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^\\d{6,20}$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[A-Za-z ]{2,50}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^\\d{4,10}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9\\s,\\-']{5,100}$");
    private static final Pattern CITY_PROVINCE_PATTERN = Pattern.compile("^[A-Za-z\\s\-']{2,50}$");
    private static final Pattern CARD_HOLDER_NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{2,50}$");

    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && CARD_NUMBER_PATTERN.matcher(cardNumber).matches() && luhnCheck(cardNumber);
    }

    public static boolean isValidCVV(String cvv) {
        return cvv != null && CVV_PATTERN.matcher(cvv).matches();
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    public static boolean isValidExpirationDate(int month, int year) {
        if (month < 1 || month > 12) return false;
        YearMonth expirationDate = YearMonth.of(year, month);
        return expirationDate.isAfter(YearMonth.now());
    }

    public static boolean isValidCountry(String country) {
        return country != null && COUNTRY_PATTERN.matcher(country).matches();
    }

    public static boolean isValidPostalCode(String postalCode) {
        return postalCode != null && POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    public static boolean isValidAddress(String address) {
        return address != null && ADDRESS_PATTERN.matcher(address).matches();
    }

    public static boolean isValidCityOrProvince(String input) {
        return input != null && CITY_PROVINCE_PATTERN.matcher(input).matches();
    }

    public static boolean isValidCardHolderName(String name) {
        return name != null && CARD_HOLDER_NAME_PATTERN.matcher(name).matches();
    }

    public static boolean validateCardInfo(String cardNumber, int expirationMonth, int expirationYear, String cvv, String cardHolderName) {
        return isValidCardNumber(cardNumber)
            && isValidExpirationDate(expirationMonth, expirationYear)
            && isValidCVV(cvv)
            && isValidCardHolderName(cardHolderName);
    }

    public static boolean validateBillingAddress(String address, String city, String province, String postalCode, String country) {
        return isValidAddress(address)
            && isValidCityOrProvince(city)
            && isValidCityOrProvince(province)
            && isValidPostalCode(postalCode)
            && isValidCountry(country);
    }

    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
