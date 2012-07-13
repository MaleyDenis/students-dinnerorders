package com.exadel.dinnerorders.ldap;

import com.exadel.dinnerorders.service.Mail;
import org.apache.commons.mail.EmailException;
import org.junit.Test;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class MailTest {
    @Test(expected = EmailException.class)
    public void testSendSimpleEmail() throws EmailException {
        Mail m = new Mail();
        m.sendSimpleEmail("dimashulgin2012@gmail.com","67912311","yex.by");

    }
}
