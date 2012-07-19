package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.service.Configuration;
import com.mysql.jdbc.Connection;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

public abstract class BaseDAO<E> implements DAO<E>{
    private Logger logger = Logger.getLogger(BaseDAO.class);

    protected Connection connection() {
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + Configuration.getProperty(SystemResource.DATABASE_HOST)
                                   + ":" + Configuration.getProperty(SystemResource.DATABASE_PORT)
                                   + "/" + Configuration.getProperty(SystemResource.DATABASE_NAME);

            String login = Configuration.getProperty(SystemResource.DATABASE_LOGIN);
            String password = Configuration.getProperty(SystemResource.DATABASE_PASSWORD);
            connection = (Connection)DriverManager.getConnection(url, login, password);
        }catch(ClassNotFoundException e){
            logger.error("BaseDAO: class has not been found.", e);
        }catch(SQLException e){
            logger.error("BaseDAO: connection has failed.", e);
        }
        return connection;
    }

    protected void disconnect(Connection connection){
        try{
            connection.close();
        }catch(SQLException e){
            logger.error("BaseDAO: connection has disconnected with errors.", e);
        }
    }
}
