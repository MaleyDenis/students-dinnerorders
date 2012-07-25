package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.PropertiesHolder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * User: Dima Shulgin
 * Date: 25.07.12
 */


public class ConnectionService {
    private static Logger logger = Logger.getLogger(ConnectionService.class);

    @PropertiesHolder(file = "development.properties")
    private static Properties devProps;


    public static Properties getDevProps() {
        return devProps;
    }

    public static void setDevProps(Properties devProps) {
        ConnectionService.devProps = devProps;
    }

    public static Properties getProdProps() {
        return prodProps;
    }

    public static void setProdProps(Properties prodProps) {
        ConnectionService.prodProps = prodProps;
    }

    @PropertiesHolder(file = "production.properties")
    private static Properties prodProps;




    public static void load() {
        Field[] fields = ConnectionService.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PropertiesHolder.class)) {
                PropertiesHolder propsHolder = field.getAnnotation(PropertiesHolder.class);
                try {
                    loadPropsAndWatch(field.getName(), propsHolder);
                } catch (Exception e) {
                    logger.error("Error in the function 'loadPropsAndWatch'!",e);
                }
            }
        }
    }


    private static void loadPropsAndWatch(String fieldName, PropertiesHolder propsHolder) {
        String propsFile = propsHolder.file();
        try {
            loadProperties(fieldName);
        } catch (Exception e) {
            logger.error("Error in the function 'loadProperties'!",e);
        }
    }


    protected static void loadProperties(String fieldName) {
        String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method setter = null;
        try {
            setter = ConnectionService.class.getDeclaredMethod(setterName, Properties.class);
            InputStream in = Configuration.class.getResourceAsStream("/properties.properties");
            Properties props = new Properties();
            props.load(in);

            setter.invoke(null, props);
        } catch (NoSuchMethodException e) {
            logger.error("Error!",e);
        } catch (InvocationTargetException e) {
            logger.error("Error!",e);
        } catch (IllegalAccessException e) {
            logger.error("Error!",e);
        } catch (IOException e) {
            logger.error("Error !",e);
        }

    }

}


