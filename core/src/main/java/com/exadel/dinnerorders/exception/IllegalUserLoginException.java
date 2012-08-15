package com.exadel.dinnerorders.exception;


import org.apache.log4j.Logger;

public class IllegalUserLoginException extends RuntimeException {
    private Logger logger = Logger.getLogger(IllegalUserLoginException.class);
    public IllegalUserLoginException(String login) {
        logger.error("User with login \"" + login + "\" was not found");
    }
}
