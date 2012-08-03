package com.exadel.dinnerorders.entity;

public enum Operation {
    UPDATE,
    ERASE;

    public static Operation getOperationByCode(char code) {
        switch (code) {
            case 'u':
                return UPDATE;
            case 'e':
                return ERASE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
