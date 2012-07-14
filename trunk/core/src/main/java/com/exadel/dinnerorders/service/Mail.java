package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class Mail {
    Email email;
    ProjectLogger projectLogger;

    public Mail() {
        projectLogger = new ProjectLogger(Configuration.class);
    }

    public void sendSimpleEmail(String sender, String sendPassword, String receiver) {

        Properties myProps = new Properties();
        Email email = new SimpleEmail();

        ///////////////using PropertiesWork
        email.setHostName(SystemResource.HOST.toString());
        email.setSmtpPort(Integer.parseInt(SystemResource.PORT.toString()));
        //////////////////////

        email.setAuthenticator(new DefaultAuthenticator(sender, sendPassword));
        email.setSSL(true);

        try {
            email.setFrom("user@gmail.com");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail.");
            email.addTo(receiver);
            email.send();

        } catch (EmailException e) {
            projectLogger.error("EmailException");
        }


    }


}

