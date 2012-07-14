package com.exadel.dinnerorders.service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 13.07.12
 */
public class Configuration {
    static  ProjectLogger   projectLogger = new ProjectLogger(Configuration.class);
    public static String getProperty(String key)  {

        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("resources\\properties.properties");
            prop.load(fis);
        } catch (FileNotFoundException e) {
           projectLogger.error("FileNotFoundException");
        } catch (IOException e) {
            projectLogger.error("IOException");
        }

        String result = prop.getProperty(key);

        return result;

    }




}
