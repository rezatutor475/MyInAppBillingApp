package com.myinappbilling.apiconfig.util;

import android.text.TextUtils;
import android.util.Patterns;

import com.myinappbilling.apiconfig.model.ApiEndpoint;
import com.myinappbilling.apiconfig.model.ApiKey;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class providing helper methods for API Configuration management.
 */
public class ApiConfigUtils {

    private static final Pattern API_KEY_PATTERN = Pattern.compile("^[A-Za-z0-9\\-_]{20,50}$");

    /**
     * Validates if the given URL is a properly formatted endpoint.
     *
     * @param url The endpoint URL to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidEndpointUrl(String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }

    /**
     * Validates the API key format.
     *
     * @param apiKey The API key string.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidApiKey(String apiKey) {
        return !TextUtils.isEmpty(apiKey) && API_KEY_PATTERN.matcher(apiKey).matches();
    }

    /**
     * Masks an API key for secure display (e.g., show only last 4 characters).
     *
     * @param apiKey The original API key.
     * @return Masked API key.
     */
    public static String maskApiKey(String apiKey) {
        if (TextUtils.isEmpty(apiKey) || apiKey.length() < 4) {
            return "****";
        }
        int visibleLength = 4;
        String maskedSection = new String(new char[apiKey.length() - visibleLength]).replace("\0", "*");
        return maskedSection + apiKey.substring(apiKey.length() - visibleLength);
    }

    /**
     * Builds a full URL from an ApiEndpoint object.
     *
     * @param endpoint The ApiEndpoint instance.
     * @return Full URL string.
     */
    public static String buildFullUrl(ApiEndpoint endpoint) {
        if (endpoint == null || TextUtils.isEmpty(endpoint.getBaseUrl()) || TextUtils.isEmpty(endpoint.getPath())) {
            return "";
        }
        return endpoint.getBaseUrl().endsWith("/") ?
                endpoint.getBaseUrl() + endpoint.getPath() :
                endpoint.getBaseUrl() + "/" + endpoint.getPath();
    }

    /**
     * Checks if an API key is expired based on expiration timestamp.
     *
     * @param apiKey The ApiKey instance.
     * @return true if expired, false otherwise.
     */
    public static boolean isApiKeyExpired(ApiKey apiKey) {
        if (apiKey == null || apiKey.getExpirationTimestamp() <= 0) {
            return false;
        }
        return System.currentTimeMillis() > apiKey.getExpirationTimestamp();
    }

    /**
     * Filters a list of API keys and returns only the active (non-expired) ones.
     *
     * @param apiKeys List of ApiKey instances.
     * @return List of active ApiKeys.
     */
    public static List<ApiKey> filterActiveApiKeys(List<ApiKey> apiKeys) {
        if (apiKeys == null || apiKeys.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        List<ApiKey> activeKeys = new java.util.ArrayList<>();
        for (ApiKey key : apiKeys) {
            if (!isApiKeyExpired(key)) {
                activeKeys.add(key);
            }
        }
        return activeKeys;
    }

    /**
     * Normalizes a URL by ensuring no duplicate slashes except in protocol.
     *
     * @param url The original URL.
     * @return Normalized URL.
     */
    public static String normalizeUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return url.replaceAll("(?<!:)//+", "/");
    }
}
