package com.myinappbilling.email.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents an email recipient with name, email address, and type (TO, CC, BCC).
 */
public class EmailRecipient {

    private String name;
    private String emailAddress;
    private RecipientType type; // TO, CC, BCC

    public enum RecipientType {
        TO,
        CC,
        BCC
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    public EmailRecipient() {
    }

    public EmailRecipient(String name, String emailAddress, RecipientType type) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public RecipientType getType() {
        return type;
    }

    public void setType(RecipientType type) {
        this.type = type;
    }

    /**
     * Validates if the email address has basic format.
     */
    public boolean isValidEmail() {
        return emailAddress != null && EMAIL_PATTERN.matcher(emailAddress).matches();
    }

    /**
     * Checks if the recipient is of a specific type.
     */
    public boolean isType(RecipientType queryType) {
        return this.type == queryType;
    }

    /**
     * Returns the domain part of the email address.
     */
    public String getEmailDomain() {
        if (emailAddress != null && emailAddress.contains("@")) {
            return emailAddress.substring(emailAddress.indexOf("@") + 1);
        }
        return "";
    }

    @Override
    public String toString() {
        return "EmailRecipient{" +
                "name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailRecipient that = (EmailRecipient) o;
        return Objects.equals(emailAddress, that.emailAddress) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, type);
    }
} 
