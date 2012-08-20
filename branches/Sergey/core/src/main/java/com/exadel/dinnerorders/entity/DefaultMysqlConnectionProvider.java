package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.exception.WorkflowException;
import com.exadel.dinnerorders.service.Configuration;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

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

    @Override
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
            ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).buildServiceRegistry();

            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }


}
