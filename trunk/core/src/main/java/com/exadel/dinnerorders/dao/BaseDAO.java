package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.service.Configuration;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

public abstract class BaseDAO<E> implements DAO<E> {
    private Logger logger = Logger.getLogger(BaseDAO.class);

    protected Connection connection() {
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

    protected void disconnect(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("BaseDAO: connection has disconnected with errors.", e);
        }
    }

    public Long getID() {
        Connection connection = connection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call getID()}");
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("MAX(ID)");
            }
        } catch (SQLException e) {
            logger.error("Error , value hasn't been returned");
        } finally {
            disconnect(connection);
        }

        logger.error("Something wrong with id generator.");
        return Math.abs(new Random().nextLong());
    }

}
