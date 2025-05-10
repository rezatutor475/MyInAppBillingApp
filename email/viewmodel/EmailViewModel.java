package com.myinappbilling.email.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.email.model.EmailMessage;
import com.myinappbilling.email.repository.EmailLogRepository;
import com.myinappbilling.email.service.EmailSenderService;

import java.util.List;

/**
 * ViewModel class for managing email-related data and interactions.
 */
public class EmailViewModel extends ViewModel {

    private final EmailSenderService emailSenderService;
    private final EmailLogRepository emailLogRepository;

    private final MutableLiveData<Boolean> emailSentStatus = new MutableLiveData<>();
    private final MutableLiveData<List<EmailMessage>> emailHistory = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public EmailViewModel() {
        this.emailSenderService = new EmailSenderService();
        this.emailLogRepository = new EmailLogRepository();
        loadEmailHistory();
    }

    /**
     * Sends an email and updates the emailSentStatus LiveData.
     *
     * @param emailMessage the email message to be sent
     */
    public void sendEmail(EmailMessage emailMessage) {
        try {
            boolean result = emailSenderService.sendEmail(emailMessage);
            emailSentStatus.setValue(result);
            if (result) {
                emailLogRepository.saveEmail(emailMessage);
                loadEmailHistory();
            }
        } catch (Exception e) {
            errorMessage.setValue("Failed to send email: " + e.getMessage());
        }
    }

    /**
     * Loads email history from repository.
     */
    private void loadEmailHistory() {
        try {
            List<EmailMessage> history = emailLogRepository.getAllEmails();
            emailHistory.setValue(history);
        } catch (Exception e) {
            errorMessage.setValue("Failed to load email history: " + e.getMessage());
        }
    }

    /**
     * Returns LiveData observing email sending status.
     *
     * @return LiveData of email sent status
     */
    public LiveData<Boolean> getEmailSentStatus() {
        return emailSentStatus;
    }

    /**
     * Returns LiveData observing email history.
     *
     * @return LiveData of list of email messages
     */
    public LiveData<List<EmailMessage>> getEmailHistory() {
        return emailHistory;
    }

    /**
     * Returns LiveData observing error messages.
     *
     * @return LiveData of error message
     */
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Refreshes the email history manually.
     */
    public void refreshEmailHistory() {
        loadEmailHistory();
    }

    /**
     * Clears the current error message.
     */
    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }
}
