package com.exadel.dinnerorders.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Dima Shulgin
 * Date: 25.07.12
 */
public class ConnectionServiceTest {
    @Before
    	public void load() {
    		try {
                ConnectionService.load();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	@Test
    	public void testProperties() throws Exception{
    	 	Assert.assertEquals("465", ConnectionService.getDevProps().getProperty("port"));
    		Assert.assertEquals("3306", ConnectionService.getProdProps().getProperty("databasePort"));
    	}



}
