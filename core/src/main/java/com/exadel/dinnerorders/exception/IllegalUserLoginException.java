package com.exadel.dinnerorders.exception;


public class IllegalUserLoginException extends RuntimeException {
    public IllegalUserLoginException(String login) {
        System.out.println("User with login " + login + " was not found");
    }
}
