package com.exadel.dinnerorders.ldap;

import org.junit.Test;

public class LdapTest {

    @Test(expected = NullPointerException.class)
    public void testCheckUserLoginForException() {
        Ldap ldap = new Ldap("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
        ldap.checkUser(null, "somePassword");
    }

    @Test(expected = IllegalUserLoginException.class)
    public void testCheckUserForIllegalUserLoginException() {
        Ldap ldap = new Ldap("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
        ldap.checkUser("badLogin", "somePassword");
    }
}