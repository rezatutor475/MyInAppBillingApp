package com.myinappbilling.payment.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * PersonalInfo stores personal identification and contact details of a user.
 */
public class PersonalInfo implements Serializable {

    private String identityCardNumber;
    private String firstName;
    private String lastName;
    private String cellphoneNumber;
    private String email;
    private String maritalStatus;
    private String educationLevel;
    private String occupation;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String country;

    public PersonalInfo() {
    }

    public PersonalInfo(String identityCardNumber, String firstName, String lastName, String cellphoneNumber,
                        String email, String maritalStatus, String educationLevel, String occupation,
                        String address, String city, String province, String postalCode, String country) {
        this.identityCardNumber = identityCardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellphoneNumber = cellphoneNumber;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.educationLevel = educationLevel;
        this.occupation = occupation;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isValid() {
        return identityCardNumber != null && !identityCardNumber.isEmpty()
                && firstName != null && !firstName.isEmpty()
                && lastName != null && !lastName.isEmpty()
                && cellphoneNumber != null && !cellphoneNumber.isEmpty()
                && email != null && email.contains("@");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getLocationSummary() {
        return address + ", " + city + ", " + province + ", " + country + " - " + postalCode;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "identityCardNumber='" + identityCardNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cellphoneNumber='" + cellphoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInfo that = (PersonalInfo) o;
        return Objects.equals(identityCardNumber, that.identityCardNumber) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(cellphoneNumber, that.cellphoneNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityCardNumber, firstName, lastName, cellphoneNumber, email);
    }
}
