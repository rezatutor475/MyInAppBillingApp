package com.myinappbilling.payment.validator;

import java.util.regex.Pattern;

/**
 * IdentityValidator provides utility methods for validating identity-related fields.
 */
public class IdentityValidator {

    private static final Pattern IDENTITY_CARD_PATTERN = Pattern.compile("^[A-Za-z0-9\-]{5,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\-' ]{2,50}$");
    private static final Pattern MARITAL_STATUS_PATTERN = Pattern.compile("^(single|married|divorced|widowed)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern EDUCATION_LEVEL_PATTERN = Pattern.compile("^(high school|associate|bachelor|master|doctorate)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern OCCUPATION_PATTERN = Pattern.compile("^[\w\s\-']{2,50}$");
    private static final Pattern CITY_PATTERN = Pattern.compile("^[A-Za-z\s\-]{2,50}$");
    private static final Pattern PROVINCE_PATTERN = Pattern.compile("^[A-Za-z\s\-]{2,50}$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[A-Za-z\s\-]{2,50}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[A-Za-z0-9\s\-]{3,10}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\+?[0-9]{7,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\w.-]+@[\w.-]+\.[A-Za-z]{2,6}$");

    public static boolean isValidIdentityCardNumber(String identityCardNumber) {
        return identityCardNumber != null && IDENTITY_CARD_PATTERN.matcher(identityCardNumber).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidFirstName(String firstName) {
        return isValidName(firstName);
    }

    public static boolean isValidLastName(String lastName) {
        return isValidName(lastName);
    }

    public static boolean isValidMaritalStatus(String status) {
        return status != null && MARITAL_STATUS_PATTERN.matcher(status).matches();
    }

    public static boolean isValidEducationLevel(String level) {
        return level != null && EDUCATION_LEVEL_PATTERN.matcher(level).matches();
    }

    public static boolean isValidOccupation(String occupation) {
        return occupation != null && OCCUPATION_PATTERN.matcher(occupation).matches();
    }

    public static boolean isValidCity(String city) {
        return city != null && CITY_PATTERN.matcher(city).matches();
    }

    public static boolean isValidProvince(String province) {
        return province != null && PROVINCE_PATTERN.matcher(province).matches();
    }

    public static boolean isValidCountry(String country) {
        return country != null && COUNTRY_PATTERN.matcher(country).matches();
    }

    public static boolean isValidPostalCode(String postalCode) {
        return postalCode != null && POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean validateIdentityInfo(String identityCardNumber, String firstName, String lastName) {
        return isValidIdentityCardNumber(identityCardNumber) &&
               isValidFirstName(firstName) &&
               isValidLastName(lastName);
    }

    public static boolean validateExtendedIdentityInfo(String identityCardNumber, String firstName, String lastName, String maritalStatus, String educationLevel, String occupation) {
        return validateIdentityInfo(identityCardNumber, firstName, lastName) &&
               isValidMaritalStatus(maritalStatus) &&
               isValidEducationLevel(educationLevel) &&
               isValidOccupation(occupation);
    }

    public static boolean validateContactInfo(String phone, String email, String city, String province, String country, String postalCode) {
        return isValidPhoneNumber(phone) &&
               isValidEmail(email) &&
               isValidCity(city) &&
               isValidProvince(province) &&
               isValidCountry(country) &&
               isValidPostalCode(postalCode);
    }
} 
