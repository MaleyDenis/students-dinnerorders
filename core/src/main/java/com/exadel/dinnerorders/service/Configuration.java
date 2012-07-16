package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 13.07.12
 */
public class Configuration {
    private static Logger logger = Logger.getLogger(MailService.class);

    public static String getProperty(SystemResource value) {

        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("core\\src\\main\\resources\\properties.properties");
            prop.load(fis);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException");
        } catch (IOException e) {
            logger.error("IOException");
        }

        String result = prop.getProperty(value.getValue());

        return result;

    }


}
