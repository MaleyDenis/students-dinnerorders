package com.exadel.dinnerorders.entity;

public enum SystemResource {
    HOST("host"),
    PORT("port"),
    LOGIN("login"),
    LDAP("ldap"),
    PASSWORD("password"),
    DATABASE_HOST("databaseHost"),
    DATABASE_PORT("databasePort"),
    DATABASE_NAME("databaseName"),
    DATABASE_LOGIN("databaseLogin"),
    DATABASE_PASSWORD("databasePass");

    public String getValue() {
        return value;
    }

    String value;

    private SystemResource(String value) {
        this.value = value;
    }

}






