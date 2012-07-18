package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 13.07.12
 */
public class Configuration {
    private static Logger logger = Logger.getLogger(MailService.class);

    public static String getProperty(SystemResource value) {

        Properties prop = new Properties();
      InputStream is = null;
        try {
            is = Configuration.class.getResourceAsStream("/properties.properties");
            prop.load(is);
        } catch (FileNotFoundException  e) {
            logger.error("File properties.properties has not been found ",e);
        } catch (IOException e) {
            logger.error("Work function named prop.load(FileInputStream f) has been interrupted",e);
        }

        String result = prop.getProperty(value.getValue());

        return result;

    }


}
