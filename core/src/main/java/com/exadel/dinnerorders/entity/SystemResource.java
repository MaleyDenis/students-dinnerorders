package com.exadel.dinnerorders.entity;

public enum SystemResource {
    HOST("host"), PORT("port");

    public String getValue() {
        return value;
    }

    String value;

    private SystemResource(String value) {
        this.value = value;
    }


}






