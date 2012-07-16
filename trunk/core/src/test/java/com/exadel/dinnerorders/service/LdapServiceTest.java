package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.exception.IllegalUserLoginException;
import com.exadel.dinnerorders.service.LdapService;
import org.junit.Test;

public class LdapServiceTest {

    @Test(expected = NullPointerException.class)
    public void testCheckUserLoginForException() {
        LdapService ldapService = new LdapService("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
        ldapService.checkUser(null, "somePassword");
    }

    @Test(expected = IllegalUserLoginException.class)
    public void testCheckUserForIllegalUserLoginException() {
        LdapService ldapService = new LdapService("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
        ldapService.checkUser("badLogin", "somePassword");
    }
}