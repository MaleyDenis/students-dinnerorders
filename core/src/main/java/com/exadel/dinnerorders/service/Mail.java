package com.exadel.dinnerorders.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class Mail {
    Email email;

    public void sendSimpleEmail(String sender, String sendPassword, String receiver) throws EmailException {

        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(sender, sendPassword));
        email.setSSL(true);
        email.setFrom("user@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail.");
        email.addTo(receiver);
        email.send();

    }


}

