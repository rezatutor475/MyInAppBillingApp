package com.myinappbilling.email.service;

import com.myinappbilling.email.model.EmailMessage;
import com.myinappbilling.email.model.EmailStatus;
import com.myinappbilling.email.validator.EmailValidator;
import com.myinappbilling.email.util.EmailUtils;

/**
 * Service responsible for sending email messages.
 */
public class EmailSenderService {

    private final EmailValidator emailValidator;

    public EmailSenderService() {
        this.emailValidator = new EmailValidator();
    }

    /**
     * Sends an email message after validating.
     *
     * @param emailMessage the email message to send
     * @return EmailStatus indicating the result
     */
    public EmailStatus sendEmail(EmailMessage emailMessage) {
        if (!emailValidator.isValidEmail(emailMessage.getRecipient().getEmail())) {
            return new EmailStatus(EmailStatus.Status.FAILED, "Invalid email address", System.currentTimeMillis());
        }

        try {
            EmailUtils.simulateEmailSending(emailMessage);
            return new EmailStatus(EmailStatus.Status.SENT, "Email sent successfully", System.currentTimeMillis());
        } catch (Exception e) {
            return new EmailStatus(EmailStatus.Status.FAILED, "Failed to send email: " + e.getMessage(), System.currentTimeMillis());
        }
    }

    /**
     * Queues an email for later delivery.
     *
     * @param emailMessage the email message to queue
     * @return EmailStatus indicating the result
     */
    public EmailStatus queueEmail(EmailMessage emailMessage) {
        // Future implementation to actually store the email in a queue
        return new EmailStatus(EmailStatus.Status.QUEUED, "Email queued for later sending", System.currentTimeMillis());
    }

    /**
     * Simulates checking the delivery status of a sent email.
     *
     * @param emailMessage the email message to check
     * @return EmailStatus indicating if delivered or bounced
     */
    public EmailStatus checkDeliveryStatus(EmailMessage emailMessage) {
        boolean delivered = EmailUtils.simulateDeliveryCheck(emailMessage);
        if (delivered) {
            return new EmailStatus(EmailStatus.Status.DELIVERED, "Email successfully delivered", System.currentTimeMillis());
        } else {
            return new EmailStatus(EmailStatus.Status.BOUNCED, "Email delivery failed", System.currentTimeMillis());
        }
    }

    /**
     * Attempts to resend a failed email.
     *
     * @param emailMessage the email message to resend
     * @return EmailStatus indicating the result
     */
    public EmailStatus resendEmail(EmailMessage emailMessage) {
        if (emailMessage == null || emailMessage.getRecipient() == null) {
            return new EmailStatus(EmailStatus.Status.FAILED, "Invalid email message", System.currentTimeMillis());
        }

        try {
            EmailUtils.simulateEmailSending(emailMessage);
            return new EmailStatus(EmailStatus.Status.SENT, "Email resent successfully", System.currentTimeMillis());
        } catch (Exception e) {
            return new EmailStatus(EmailStatus.Status.FAILED, "Failed to resend email: " + e.getMessage(), System.currentTimeMillis());
        }
    }
}