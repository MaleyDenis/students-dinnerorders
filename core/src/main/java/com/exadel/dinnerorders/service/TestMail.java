package com.exadel.education.web.student.dao;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {

    private  boolean exception = false;

    public  boolean isException() {
        return exception;
    }

    public  void send() {
        final String username = "dimashulgin2012@gmail.com";
        final String password = "67912311";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dimashulgin2012@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("shulgindima2011@yandex.ru"));
            message.setSubject("Testing Subject");
            message.setText("Dear User,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            exception = true;
        }
    }
}
    
   