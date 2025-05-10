package com.myinappbilling.email.repository;

import com.myinappbilling.email.model.EmailMessage;
import com.myinappbilling.email.model.EmailStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for logging sent emails and their statuses.
 */
public class EmailLogRepository {

    private final List<EmailLogEntry> emailLogs;

    public EmailLogRepository() {
        this.emailLogs = new ArrayList<>();
    }

    /**
     * Logs an email message along with its status.
     *
     * @param emailMessage the email message
     * @param status the status of the email
     */
    public void logEmail(EmailMessage emailMessage, EmailStatus status) {
        emailLogs.add(new EmailLogEntry(emailMessage, status, LocalDateTime.now()));
    }

    /**
     * Retrieves an unmodifiable list of all email logs.
     *
     * @return list of email log entries
     */
    public List<EmailLogEntry> getAllLogs() {
        return Collections.unmodifiableList(emailLogs);
    }

    /**
     * Retrieves logs filtered by a specific status.
     *
     * @param status the status to filter by
     * @return list of email log entries matching the status
     */
    public List<EmailLogEntry> getLogsByStatus(EmailStatus status) {
        return emailLogs.stream()
                .filter(log -> log.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves logs sent to a specific recipient email.
     *
     * @param recipientEmail the recipient email to filter by
     * @return list of email log entries matching the recipient
     */
    public List<EmailLogEntry> getLogsByRecipient(String recipientEmail) {
        return emailLogs.stream()
                .filter(log -> log.getEmailMessage().getRecipient().getEmailAddress().equalsIgnoreCase(recipientEmail))
                .collect(Collectors.toList());
    }

    /**
     * Clears all email logs.
     */
    public void clearLogs() {
        emailLogs.clear();
    }

    /**
     * Data class representing a logged email entry.
     */
    public static class EmailLogEntry {
        private final EmailMessage emailMessage;
        private final EmailStatus status;
        private final LocalDateTime timestamp;

        public EmailLogEntry(EmailMessage emailMessage, EmailStatus status, LocalDateTime timestamp) {
            this.emailMessage = emailMessage;
            this.status = status;
            this.timestamp = timestamp;
        }

        public EmailMessage getEmailMessage() {
            return emailMessage;
        }

        public EmailStatus getStatus() {
            return status;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
