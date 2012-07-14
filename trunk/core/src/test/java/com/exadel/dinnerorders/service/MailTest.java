package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import junit.framework.Assert;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class MailTest {
    @Test
    public void testSendSimpleEmail() {
        Properties myProps = new Properties();
        Email email = new SimpleEmail();

        ///////////////using PropertiesWork
        email.setHostName(SystemResource.HOST.toString());

        try {
            email.setSmtpPort(Integer.parseInt(SystemResource.PORT.toString()));
            Assert.assertTrue(true);
        } catch (NumberFormatException e) {
            Assert.assertTrue(false);

        }

    }
    // public void testSendSimpleEmail() {
    //     Mail m = new Mail();
    //     try {

    //          m.sendSimpleEmail("dimashulgin2012@gmail.com", "67912311", "shulgindima2011@yandex.ru");
    //        Assert.assertTrue(true);
    //  } catch (EmailException e) {
    //    Assert.assertTrue(false);
    //  }

    //}
}
