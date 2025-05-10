package com.myinappbilling.payment.util;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Utility class for common payment-related validations and formatting.
 */
public class PaymentUtils {

    private static final Pattern IDENTITY_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,20}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[0-9A-Za-z]{4,10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s'-]{1,50}$");
    private static final Pattern CITY_PATTERN = Pattern.compile("^[A-Za-z\\s'-]{1,50}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9\\s.,#'-]{5,100}$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[A-Za-z\\s'-]{2,56}$");
    private static final Pattern OCCUPATION_PATTERN = Pattern.compile("^[A-Za-z\\s'-]{2,50}$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{8,20}$");

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidCardNumber(String cardNumber) {
        if (TextUtils.isEmpty(cardNumber) || cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }
        return luhnCheck(cardNumber);
    }

    public static boolean isValidCVV(String cvv) {
        return !TextUtils.isEmpty(cvv) && cvv.matches("\\d{3,4}");
    }

    public static boolean isValidExpiry(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        return (year > currentYear) || (year == currentYear && month >= currentMonth);
    }

    public static boolean isValidPostalCode(String postalCode) {
        return !TextUtils.isEmpty(postalCode) && POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    public static boolean isValidIdentityCard(String id) {
        return !TextUtils.isEmpty(id) && IDENTITY_PATTERN.matcher(id).matches();
    }

    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidCity(String city) {
        return !TextUtils.isEmpty(city) && CITY_PATTERN.matcher(city).matches();
    }

    public static boolean isValidAddress(String address) {
        return !TextUtils.isEmpty(address) && ADDRESS_PATTERN.matcher(address).matches();
    }

    public static boolean isValidCountry(String country) {
        return !TextUtils.isEmpty(country) && COUNTRY_PATTERN.matcher(country).matches();
    }

    public static boolean isValidOccupation(String occupation) {
        return !TextUtils.isEmpty(occupation) && OCCUPATION_PATTERN.matcher(occupation).matches();
    }

    public static boolean isValidEducationLevel(String education) {
        return !TextUtils.isEmpty(education);
    }

    public static boolean isValidMaritalStatus(String status) {
        return !TextUtils.isEmpty(status);
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        return !TextUtils.isEmpty(accountNumber) && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    public static boolean isValidExpirationMonth(int month) {
        return month >= 1 && month <= 12;
    }

    public static boolean isValidExpirationYear(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return year >= currentYear && year <= currentYear + 10;
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
