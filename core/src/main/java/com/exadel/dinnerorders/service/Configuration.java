package com.exadel.dinnerorders.service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * User: Dmitry Shulgin
 * Date: 13.07.12
 */
public class Configuration {

    public static String getProperty(String key) throws IOException {


        Properties prop = new Properties();
        FileInputStream fis = null;



            fis = new FileInputStream("resources\\properties.properties");
            prop.load(fis);




        String result = prop.getProperty(key);

        return result;

    }




}
