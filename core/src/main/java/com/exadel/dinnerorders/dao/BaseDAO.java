package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DefaultConnection;
import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.exception.WorkflowException;
import com.exadel.dinnerorders.service.Configuration;
import com.exadel.dinnerorders.service.DefaultConnectionProvider;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.sql.*;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

public abstract class BaseDAO<E> implements DAO<E> {
    private Logger logger = Logger.getLogger(BaseDAO.class);

    protected Connection connection(Class clazz) {

        Annotation[] annotations = clazz.getAnnotations();
        int size = annotations.length;
        for (int i = 0; i < size; ++i) {
            if (annotations[i].annotationType().equals(DefaultConnection.class)) {
                return DefaultConnectionProvider.connection();
            }
        }

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
        Connection connection = connection(this.getClass());

        try {

            CallableStatement callableStatement = connection.prepareCall("{call getID(?)}");
            callableStatement.registerOutParameter("idOUT", Types.INTEGER);
            boolean hadResults = callableStatement.execute();
            if (!hadResults)
                return callableStatement.getLong(1);


        } catch (SQLException e) {
            logger.error("Error , value hasn't been returned");
        } finally {
            disconnect(connection);
        }

        throw new WorkflowException();
    }
}


