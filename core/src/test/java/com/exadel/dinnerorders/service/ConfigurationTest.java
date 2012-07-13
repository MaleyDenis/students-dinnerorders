package com.exadel.dinnerorders.service;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;


public class ConfigurationTest {
    @Test
    public void testGetProperty() {
        try {

            Configuration.getProperty("host");

            Assert.assertTrue(true);
        }  catch (IOException e) {
            Assert.assertTrue(false);
        }
    }


}
