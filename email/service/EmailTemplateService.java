package com.myinappbilling.email.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Service responsible for managing email templates.
 */
public class EmailTemplateService {

    private final Map<String, String> templates;

    public EmailTemplateService() {
        this.templates = new HashMap<>();
        loadDefaultTemplates();
    }

    /**
     * Loads default email templates into the service.
     */
    private void loadDefaultTemplates() {
        templates.put("WELCOME", "Hello {name}, welcome to our service!");
        templates.put("RESET_PASSWORD", "Hi {name}, use this code to reset your password: {code}");
        templates.put("NOTIFICATION", "Dear {name}, you have a new notification.");
        templates.put("ACCOUNT_VERIFICATION", "Hi {name}, please verify your account using this link: {verification_link}");
        templates.put("SUBSCRIPTION_RENEWAL", "Hello {name}, your subscription has been renewed successfully.");
        templates.put("PAYMENT_FAILURE", "Hi {name}, your payment could not be processed. Please update your payment method.");
    }

    /**
     * Retrieves a template by its key.
     *
     * @param key the key of the template
     * @return the template string, or null if not found
     */
    public String getTemplate(String key) {
        return templates.get(key);
    }

    /**
     * Adds or updates a template.
     *
     * @param key the key for the template
     * @param template the template content
     */
    public void addOrUpdateTemplate(String key, String template) {
        templates.put(key, template);
    }

    /**
     * Deletes a template by its key.
     *
     * @param key the key of the template to delete
     * @return true if the template was removed, false otherwise
     */
    public boolean deleteTemplate(String key) {
        return templates.remove(key) != null;
    }

    /**
     * Lists all available template keys.
     *
     * @return set of all template keys
     */
    public Set<String> listTemplateKeys() {
        return templates.keySet();
    }

    /**
     * Fills a template with provided parameters.
     *
     * @param template the template string with placeholders
     * @param parameters a map of placeholder names to their values
     * @return the filled-in template
     */
    public String fillTemplate(String template, Map<String, String> parameters) {
        if (template == null || parameters == null) {
            return template;
        }
        String filledTemplate = template;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            filledTemplate = filledTemplate.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return filledTemplate;
    }

    /**
     * Checks if a template exists by its key.
     *
     * @param key the key to check
     * @return true if the template exists, false otherwise
     */
    public boolean templateExists(String key) {
        return templates.containsKey(key);
    }

    /**
     * Clears all templates.
     */
    public void clearTemplates() {
        templates.clear();
    }

    /**
     * Counts the number of templates stored.
     *
     * @return the number of templates
     */
    public int countTemplates() {
        return templates.size();
    }
}
