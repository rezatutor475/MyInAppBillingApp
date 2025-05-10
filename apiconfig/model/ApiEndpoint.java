package com.myinappbilling.apiconfig.model;

import java.util.Objects;

/**
 * Represents an API endpoint configuration with advanced features.
 */
public class ApiEndpoint {

    private String name;
    private String url;
    private String method; // GET, POST, PUT, DELETE
    private boolean requiresAuthentication;
    private int timeout; // in milliseconds
    private boolean isDeprecated;

    public ApiEndpoint() {
    }

    public ApiEndpoint(String name, String url, String method, boolean requiresAuthentication, int timeout, boolean isDeprecated) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.requiresAuthentication = requiresAuthentication;
        this.timeout = timeout;
        this.isDeprecated = isDeprecated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isRequiresAuthentication() {
        return requiresAuthentication;
    }

    public void setRequiresAuthentication(boolean requiresAuthentication) {
        this.requiresAuthentication = requiresAuthentication;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void setDeprecated(boolean deprecated) {
        isDeprecated = deprecated;
    }

    public boolean isSecureEndpoint() {
        return url != null && url.startsWith("https");
    }

    @Override
    public String toString() {
        return "ApiEndpoint{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", requiresAuthentication=" + requiresAuthentication +
                ", timeout=" + timeout +
                ", isDeprecated=" + isDeprecated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiEndpoint that = (ApiEndpoint) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }
}
