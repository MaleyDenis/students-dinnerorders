package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.exception.IllegalUserLoginException;
import junit.framework.Assert;
import org.junit.Test;

public class LdapServiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCheckUserLoginForException() {
        LdapService ldapService = new LdapService();
        ldapService.checkUser(null, "somePassword");
    }

    @Test(expected = IllegalUserLoginException.class)
    public void testCheckUserForIllegalUserLoginException() {
        LdapService ldapService = new LdapService();
        ldapService.checkUser("badLogin", "somePassword");
    }

    @Test
    public void testLoadAllForNotNull(){
        LdapService ldapService = new LdapService();
        Assert.assertNotNull(ldapService.loadAll());
    }
}