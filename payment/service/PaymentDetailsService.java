package com.myinappbilling.payment.service;

import com.myinappbilling.payment.model.PaymentDetails;
import com.myinappbilling.payment.repository.PaymentDetailsRepository;
import com.myinappbilling.payment.validator.CardValidator;
import com.myinappbilling.payment.validator.IdentityValidator;
import com.myinappbilling.payment.validator.PaymentValidator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PaymentDetailsService provides business logic for managing payment details.
 */
public class PaymentDetailsService {

    private final PaymentDetailsRepository repository;

    public PaymentDetailsService(PaymentDetailsRepository repository) {
        this.repository = repository;
    }

    public boolean savePaymentDetails(PaymentDetails paymentDetails) {
        if (!validatePaymentDetails(paymentDetails)) {
            return false;
        }
        repository.save(paymentDetails);
        return true;
    }

    public PaymentDetails getPaymentDetailsById(String id) {
        return repository.findById(id);
    }

    public List<PaymentDetails> getAllPaymentDetails() {
        return repository.findAll();
    }

    public List<PaymentDetails> findPaymentsByCountry(String country) {
        return repository.findAll().stream()
            .filter(pd -> country.equalsIgnoreCase(pd.getPersonalInfo().getCountry()))
            .collect(Collectors.toList());
    }

    public List<PaymentDetails> findPaymentsByCardIssuer(String issuer) {
        return repository.findAll().stream()
            .filter(pd -> pd.getCardInfo().getCardNumber().startsWith(issuer))
            .collect(Collectors.toList());
    }

    public boolean updatePaymentDetails(String id, PaymentDetails updatedDetails) {
        if (!validatePaymentDetails(updatedDetails)) {
            return false;
        }
        repository.update(id, updatedDetails);
        return true;
    }

    public boolean deletePaymentDetails(String id) {
        return repository.delete(id);
    }

    public boolean isCardExpiringSoon(PaymentDetails paymentDetails, int monthsThreshold) {
        int currentYear = java.time.Year.now().getValue();
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        int expirationYear = paymentDetails.getCardInfo().getExpirationYear();
        int expirationMonth = paymentDetails.getCardInfo().getExpirationMonth();

        int totalCurrentMonths = currentYear * 12 + currentMonth;
        int totalExpirationMonths = expirationYear * 12 + expirationMonth;

        return (totalExpirationMonths - totalCurrentMonths) <= monthsThreshold;
    }

    public long countExpiredCards() {
        int currentYear = java.time.Year.now().getValue();
        int currentMonth = java.time.LocalDate.now().getMonthValue();
        return repository.findAll().stream()
            .filter(pd -> {
                int expirationYear = pd.getCardInfo().getExpirationYear();
                int expirationMonth = pd.getCardInfo().getExpirationMonth();
                return expirationYear < currentYear || (expirationYear == currentYear && expirationMonth < currentMonth);
            })
            .count();
    }

    public List<PaymentDetails> searchByEmail(String email) {
        return repository.findAll().stream()
            .filter(pd -> pd.getPersonalInfo().getEmail().equalsIgnoreCase(email))
            .collect(Collectors.toList());
    }

    private boolean validatePaymentDetails(PaymentDetails details) {
        return IdentityValidator.validatePersonalInfo(details.getPersonalInfo()) &&
               CardValidator.validateCardInfo(
                   details.getCardInfo().getCardNumber(),
                   details.getCardInfo().getExpirationMonth(),
                   details.getCardInfo().getExpirationYear(),
                   details.getCardInfo().getCvv()
               ) &&
               PaymentValidator.validatePaymentDetails(details);
    }
}
