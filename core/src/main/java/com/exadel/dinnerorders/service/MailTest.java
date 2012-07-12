package com.exadel.dinnerorders.service;

import org.apache.commons.mail.EmailException;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: 12
 * Date: 12.07.12
 * Time: 20:13
 * To change this template use File | Settings | File Templates.
 */
public class MailTest {
    @Test(expected = EmailException.class)
    public void testSendSimpleEmail() throws EmailException {
        Mail m = new Mail();
        m.sendSimpleEmail("dimashulgin2012@gmail.com","67912311","yex.by");

    }
}
