package com.exadel.dinnerorders.ldap;

import com.exadel.dinnerorders.service.Mail;
import junit.framework.Assert;
import org.apache.commons.mail.EmailException;
import org.junit.Test;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class MailTest {
     @Test
    public void testSendSimpleEmail() {
        Mail m = new Mail();
        try {

            m.sendSimpleEmail("dimashulgin2012@gmail.com", "67912311", "shulgindima2011@yandex.ru");
            Assert.assertTrue(true);
        } catch (EmailException e) {
            Assert.assertTrue(false);
        }

    }
}
