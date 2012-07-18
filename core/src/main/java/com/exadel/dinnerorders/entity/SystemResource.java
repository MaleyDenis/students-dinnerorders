package com.exadel.dinnerorders.entity;

public enum SystemResource {
    HOST("host"),
    PORT("port"),
    LOGIN("login"),
    LDAP("ldap"),
    PASSWORD("password");

    public String getValue() {
        return value;
    }

    String value;

    private SystemResource(String value) {
        this.value = value;
    }

}





