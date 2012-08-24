package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.exception.WorkflowException;
import com.exadel.dinnerorders.service.Configuration;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * User: Dima Shulgin
 * Date: 25.07.12
 */

public class DefaultMysqlConnectionProvider implements MysqlConnectionProvider {
    private static Logger logger = Logger.getLogger(DefaultMysqlConnectionProvider.class);

    public Connection connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + Configuration.getProperty(SystemResource.DATABASE_HOST)
                    + ":" + Configuration.getProperty(SystemResource.DATABASE_PORT)
                    + "/" + Configuration.getProperty(SystemResource.DATABASE_NAME);

            String login = Configuration.getProperty(SystemResource.DATABASE_LOGIN);
            String password = Configuration.getProperty(SystemResource.DATABASE_PASSWORD);

            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException e) {
            logger.error("BaseDAO: class has not been found.", e);
            throw new WorkflowException(e);
        } catch (SQLException e) {
            logger.error("BaseDAO: getConnection has failed.", e);
            throw new WorkflowException(e);
        }
        return connection;
    }

    public SessionFactory getSessionFactory () {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}
