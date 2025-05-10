package com.myinappbilling.email.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents an email message with subject, body, sender, recipients, and metadata.
 */
public class EmailMessage {

    private String id;
    private String subject;
    private String body;
    private String sender;
    private List<String> recipients;
    private Date timestamp;
    private boolean isHtml;
    private boolean isRead;
    private boolean isFlagged;
    private String replyTo;
    private List<String> cc;
    private List<String> bcc;
    private List<String> attachments;

    public EmailMessage() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = new Date();
        this.isRead = false;
        this.isFlagged = false;
    }

    public EmailMessage(String subject, String body, String sender, List<String> recipients, Date timestamp, boolean isHtml) {
        this();
        this.subject = subject;
        this.body = body;
        this.sender = sender;
        this.recipients = recipients;
        this.timestamp = timestamp;
        this.isHtml = isHtml;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void markAsUnread() {
        this.isRead = false;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        this.isFlagged = flagged;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", sender='" + sender + '\'' +
                ", recipients=" + recipients +
                ", timestamp=" + timestamp +
                ", isHtml=" + isHtml +
                ", isRead=" + isRead +
                ", isFlagged=" + isFlagged +
                ", replyTo='" + replyTo + '\'' +
                ", cc=" + cc +
                ", bcc=" + bcc +
                ", attachments=" + attachments +
                '}';
    }
}
