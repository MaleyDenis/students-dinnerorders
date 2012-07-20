package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.log4j.Logger;

import java.sql.*;

public class UniqueIDService {
    private static final Logger logger = Logger.getLogger(UniqueIDService.class);
    private static Long ID;

    synchronized public static Long getID() {
        if (ID == null) {
            loadLastUsedID();
        }
        Long retID = ID;
        ID++;
        saveNewID();
        return retID;
    }

    private static void saveNewID() {
        Connection connection = createConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("UPDATE id_table SET id = ?;");
            preparedStatement.setLong(1, ID);
            preparedStatement.close();
            connection.close();
        } catch (SQLException sqlException) {
            logger.error("UniqueIDService: can't save new used id");
        }
    }

    private static void loadLastUsedID() {
        Connection connection = createConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dinnerorders.id_table");
            resultSet.next();
            ID = resultSet.getLong(1);
            statement.close();
            connection.close();
        } catch (SQLException sqlException) {
            logger.error("UniqueIDService: can't load last used id");
        }
    }

    private static Connection createConnection() {
        com.mysql.jdbc.Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + Configuration.getProperty(SystemResource.DATABASE_HOST)
                    + ":" + Configuration.getProperty(SystemResource.DATABASE_PORT)
                    + "/" + Configuration.getProperty(SystemResource.DATABASE_NAME);

            String login = Configuration.getProperty(SystemResource.DATABASE_LOGIN);
            String password = Configuration.getProperty(SystemResource.DATABASE_PASSWORD);
            connection = (com.mysql.jdbc.Connection) DriverManager.getConnection(url, login, password);
        }catch(ClassNotFoundException e){
            logger.error("UniqueIDService: class has not been found.", e);
        }catch(SQLException e){
            logger.error("UniqueIDService: connection has failed.", e);
        }
        return connection;
    }
}
