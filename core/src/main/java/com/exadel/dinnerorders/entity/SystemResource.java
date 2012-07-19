package com.exadel.dinnerorders.entity;

public enum SystemResource {
    HOST("host"),
    PORT("port"),
    LOGIN("login"),
    PASSWORD("password"),
    DATABASE_HOST("databaseHost"),
    DATABASE_PORT("databasePort"),
    DATABASE_NAME("databaseName"),
    DATABASE_LOGIN("databaseLogin"),
    DATABASE_PASSWORD("databasePass"),
    LDAP_HOST("ldapHost"),
    LDAP_SEARCHING_ATTRIBUTES("ldapSearchingAttributes"),
    LDAP_SEARCHING_FILTER("ldapSearchingFilter"),
    LDAP_SEARCHING_START_BASE("ldapSearchingStartBase"),
    LDAP_AUTHENTICATION_TYPE ("ldapAuthenticationType");

    public String getValue() {
        return value;
    }

    String value;

    private SystemResource(String value) {
        this.value = value;
    }

}






