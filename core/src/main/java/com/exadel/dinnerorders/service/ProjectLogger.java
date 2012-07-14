package com.exadel.dinnerorders.service;


// * User: Dima Shulgin
// * Date: 14.07.12

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ProjectLogger {

    public Logger logger;

    public ProjectLogger(Class clazz) {
        logger = Logger.getLogger(clazz);
        DOMConfigurator.configure("resources\\log4j.xml");
    }

    public void error(String message) {
        logger.error(message);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }


}