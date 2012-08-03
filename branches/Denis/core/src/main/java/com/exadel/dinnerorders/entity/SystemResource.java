package com.exadel.dinnerorders.entity;

public enum SystemResource {
    HOST("host"),
    PORT("port"),
    LOGIN("login"),
    PASSWORD("password"),

    DATABASE_HOST("mysqlDatabaseHost"),
    DATABASE_PORT("mysqlDatabasePort"),
    DATABASE_NAME("mysqlDatabaseName"),
    DATABASE_LOGIN("mysqlDatabaseLogin"),
    DATABASE_PASSWORD("mysqlDatabasePass"),

    LDAP_HOST("ldapHost"),
    LDAP_SEARCHING_ATTRIBUTES("ldapSearchingAttributes"),
    LDAP_SEARCHING_FILTER("ldapSearchingFilter"),
    LDAP_SEARCHING_START_BASE("ldapSearchingStartBase"),
    LDAP_AUTHENTICATION_TYPE ("ldapAuthenticationType"),

    MONGODB_HOST("mongoDatabaseHost"),
    MONGODB_PORT("mongoDatabasePort"),
    MONGODB_NAME("mongoDatabaseName"),
    MONGODB_LOGIN("mongoDatabaseLogin"),
    MONGODB_PASSWORD("mongoDatabasePass"),

    DELETION_SERVICE_START_DELAY("initialDelay"),
    DELETION_SERVICE_INTERVAL_DELAY("intervalDelay"),
    TIME_UNIT("timeUnit"),
    ELAPSED_TIME("elapsedTimeForDeletion");


    public String getValue() {
        return value;
    }

    String value;

    private SystemResource(String value) {
        this.value = value;
    }

}






