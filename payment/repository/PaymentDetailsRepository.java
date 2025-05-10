package com.myinappbilling.payment.repository;

import com.myinappbilling.payment.model.PaymentDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A simple in-memory repository for storing PaymentDetails.
 * In a real application, this would connect to a database.
 */
public class PaymentDetailsRepository {

    private final List<PaymentDetails> paymentDetailsStore = new ArrayList<>();

    /**
     * Saves a new PaymentDetails object.
     *
     * @param details the payment details to save
     * @return the saved object with assigned ID
     */
    public PaymentDetails save(PaymentDetails details) {
        details.setId(UUID.randomUUID().toString());
        paymentDetailsStore.add(details);
        return details;
    }

    /**
     * Retrieves a PaymentDetails object by ID.
     *
     * @param id the ID to search
     * @return an Optional of PaymentDetails
     */
    public Optional<PaymentDetails> findById(String id) {
        return paymentDetailsStore.stream()
                .filter(details -> id.equals(details.getId()))
                .findFirst();
    }

    /**
     * Returns all saved payment details.
     *
     * @return list of all PaymentDetails
     */
    public List<PaymentDetails> findAll() {
        return new ArrayList<>(paymentDetailsStore);
    }

    /**
     * Finds payment details by email.
     *
     * @param email the email address to search
     * @return list of matching PaymentDetails
     */
    public List<PaymentDetails> findByEmail(String email) {
        return paymentDetailsStore.stream()
                .filter(details -> details.getPersonalInfo() != null &&
                                   email.equalsIgnoreCase(details.getPersonalInfo().getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Finds payment details by full name.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @return list of matching PaymentDetails
     */
    public List<PaymentDetails> findByFullName(String firstName, String lastName) {
        return paymentDetailsStore.stream()
                .filter(details -> details.getPersonalInfo() != null &&
                        firstName.equalsIgnoreCase(details.getPersonalInfo().getFirstName()) &&
                        lastName.equalsIgnoreCase(details.getPersonalInfo().getLastName()))
                .collect(Collectors.toList());
    }

    /**
     * Finds payment details by account number.
     *
     * @param accountNumber the account number
     * @return Optional of matching PaymentDetails
     */
    public Optional<PaymentDetails> findByAccountNumber(String accountNumber) {
        return paymentDetailsStore.stream()
                .filter(details -> accountNumber.equals(details.getAccountNumber()))
                .findFirst();
    }

    /**
     * Updates an existing PaymentDetails object.
     *
     * @param updatedDetails the updated payment details
     * @return true if update was successful
     */
    public boolean update(PaymentDetails updatedDetails) {
        return findById(updatedDetails.getId()).map(existing -> {
            paymentDetailsStore.remove(existing);
            paymentDetailsStore.add(updatedDetails);
            return true;
        }).orElse(false);
    }

    /**
     * Deletes a PaymentDetails object by ID.
     *
     * @param id the ID of the payment details to delete
     * @return true if deletion was successful
     */
    public boolean deleteById(String id) {
        return paymentDetailsStore.removeIf(details -> id.equals(details.getId()));
    }

    /**
     * Clears all stored payment details.
     */
    public void clearAll() {
        paymentDetailsStore.clear();
    }

    /**
     * Counts the total number of stored payment records.
     *
     * @return total number of records
     */
    public int count() {
        return paymentDetailsStore.size();
    }

    /**
     * Checks if a payment detail with a given email already exists.
     *
     * @param email the email address to check
     * @return true if email already exists
     */
    public boolean existsByEmail(String email) {
        return paymentDetailsStore.stream()
                .anyMatch(details -> details.getPersonalInfo() != null &&
                        email.equalsIgnoreCase(details.getPersonalInfo().getEmail()));
    }

    /**
     * Checks if a card number already exists.
     *
     * @param cardNumber the card number to check
     * @return true if the card number exists
     */
    public boolean existsByCardNumber(String cardNumber) {
        return paymentDetailsStore.stream()
                .anyMatch(details -> details.getCardInfo() != null &&
                        cardNumber.equals(details.getCardInfo().getCardNumber()));
    }
} 
