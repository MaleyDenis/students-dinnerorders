package com.exadel.dinnerorders.entity;

/**
 * User: Dima Shulgin
 * Date: 18.07.12
 */
public enum Role {
    USER("user"),ADMIN("admin");

    public String getValue() {
        return value;
    }

    String value;

    private Role(String value) {
        this.value = value;
    }
}
