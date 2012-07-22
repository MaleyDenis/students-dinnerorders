package com.exadel.dinnerorders.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
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
        try {
            CallableStatement callableStatement = connection().prepareCall("{call getID()}");
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("MAX(ID)");
            }
        } catch (SQLException e) {
            logger.error("Error , value hasn't been returned");
        }


        return null;
    }

    public boolean dropTable() {
        com.mysql.jdbc.Connection connection = connection();

        try {
            com.mysql.jdbc.PreparedStatement preparedStatement = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(("DELETE FROM identifier WHERE id>0"));
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error("Error in the  drop  function", e);
            return false;
        } finally {
            disconnect(connection);
        }
        return true;
    }


    public boolean create(Long newItem) {
        Connection connection = connection();
        try {
            PreparedStatement preparedStatement =
                    preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT into identifier SET  ID = newItem");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error in the function create", e);

        } finally {
            disconnect(connection);
        }

        return true;
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


