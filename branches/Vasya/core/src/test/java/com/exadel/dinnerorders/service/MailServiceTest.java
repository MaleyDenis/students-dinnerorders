package com.exadel.dinnerorders.service;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Dmitry Shulgin
 * Date: 12.07.12
 */
public class MailServiceTest {
    @Test
    public void testSendSimpleEmail() {
       MailService mailService = new MailService();

        try {
           mailService.sendSimpleEmail("dimashulgin2012@gmail.com");
            Assert.assertTrue(true);
        } catch (NumberFormatException e) {
            Assert.assertTrue(false);

        }

    }

}
