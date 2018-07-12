package com.crud.tasks.domain;

import lombok.*;

@Getter
@AllArgsConstructor
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
}
