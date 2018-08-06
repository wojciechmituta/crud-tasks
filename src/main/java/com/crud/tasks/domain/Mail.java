package com.crud.tasks.domain;

public class Mail {

    private String mailTo;
    private String toCc;
    private String subject;
    private String message;

    public Mail(String mailTo, String subject, String message) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }

    @java.beans.ConstructorProperties({"mailTo", "toCc", "subject", "message"})
    public Mail(String mailTo, String toCc, String subject, String message) {
        this.mailTo = mailTo;
        this.toCc = toCc;
        this.subject = subject;
        this.message = message;
    }

    public String getMailTo() {
        return this.mailTo;
    }

    public String getToCc() {
        return this.toCc;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getMessage() {
        return this.message;
    }
}
