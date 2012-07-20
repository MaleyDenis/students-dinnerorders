package com.exadel.dinnerorders.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 20.07.12
 */
public class IdDAO extends BaseDAO<Long> {

    private Logger logger = Logger.getLogger(IdDAO.class);


    public Long getID() {

        Long maxIndex;
        Connection connection = connection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT MAX(ID) FROM (identifier)"));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxIndex = new Long(resultSet.getInt("MAX(ID)"));
                return maxIndex;
            }


        } catch (SQLException e) {
            logger.error("Error! Don't get max value of index", e);
            return null;
        } finally {
            disconnect(connection);
        }

        return null;
    }

    public boolean setID(Long newID) {
        Connection connection = connection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("INSERT into identifier (ID) VALUE (?);"));
            preparedStatement.setLong(1, newID);
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error("Error! Don't set  value of id", e);
            return false;
        } finally {
            disconnect(connection);
        }

        return true;
    }

    public boolean create(Long newItem) {
        return false;  //not used
    }

    public boolean update(Long item) {
        return false;  //not used
    }

    public boolean delete(Long item) {
        return false; //not used
    }

    public Long load(Long value) {
        return null;  //not used
    }

    public Collection<Long> loadAll() {
        return null; //not used
    }


}


