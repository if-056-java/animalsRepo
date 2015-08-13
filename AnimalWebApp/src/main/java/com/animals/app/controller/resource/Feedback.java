package com.animals.app.controller.resource;

/**
 * Created by aquaneo on 8/13/2015.
 */
public class Feedback {
    private Mail mail;
    private String gRecaptchaResponse;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public String getgRecaptchaResponse() {
        return gRecaptchaResponse;
    }

    public void setgRecaptchaResponse(String gRecaptchaResponse) {
        this.gRecaptchaResponse = gRecaptchaResponse;
    }
}

class Mail {
    private String signup;
    private String email;
    private String text;

    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}