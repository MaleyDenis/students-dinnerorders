package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import junit.framework.Assert;
import org.junit.Test;


public class ConfigurationTest {
    @Test
    public void testGetProperty() {

        if (Configuration.getProperty(SystemResource.LOGIN).equals("dimashulgin2012@gmail.com")) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

    }


}
