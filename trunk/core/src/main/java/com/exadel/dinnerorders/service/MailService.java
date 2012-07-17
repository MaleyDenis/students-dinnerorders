package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class MailService {
    private Logger logger = Logger.getLogger(MailService.class);

    public MailService() {
    }

    public void sendSimpleEmail(String receiver) {

        Properties myProps = new Properties();
        Email email = new SimpleEmail();

        ///////////////using PropertiesWork
        email.setHostName(Configuration.getProperty(SystemResource.HOST));
        email.setSmtpPort(Integer.parseInt(Configuration.getProperty(SystemResource.PORT)));
        //////////////////////

        email.setAuthenticator(new DefaultAuthenticator(Configuration.getProperty(SystemResource.LOGIN), Configuration.getProperty(SystemResource.PASSWORD)));
        email.setSSL(true);

        try {
            email.setFrom("user@gmail.com");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail.");
            email.addTo(receiver);
            email.send();

        } catch (EmailException e) {
            logger.error("Email of receiver has not been found ",e);
        }


    }


}

