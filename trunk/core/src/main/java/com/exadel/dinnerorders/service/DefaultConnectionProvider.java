package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * User: Dima Shulgin
 * Date: 25.07.12
 */
public class DefaultConnectionProvider {
    private static Logger logger = Logger.getLogger(DefaultConnectionProvider.class);

    public  static Connection connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + Configuration.getProperty(SystemResource.DATABASE_HOST)
                    + ":" + Configuration.getProperty(SystemResource.DATABASE_PORT)
                    + "/" + Configuration.getProperty(SystemResource.DATABASE_NAME);

            String login = Configuration.getProperty(SystemResource.DATABASE_LOGIN);
            String password = Configuration.getProperty(SystemResource.DATABASE_PASSWORD);

            logger.debug("URL: " + url + "; Login: " + login + "; Password: " + password + ";");

            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException e) {
            logger.error("BaseDAO: class has not been found.", e);
        } catch (SQLException e) {
            logger.error("BaseDAO: connection has failed.", e);
        }
        return connection;
    }
}
