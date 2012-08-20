package com.exadel.dinnerorders.exception;


import org.apache.log4j.Logger;

public class IllegalUserLoginException extends RuntimeException {
    public IllegalUserLoginException(String login) {
        Logger logger = Logger.getLogger(IllegalUserLoginException.class);
        logger.error("User with login \"" + login + "\" was not found");
    }
}
