package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
public class UserDAO extends BaseDAO<User> {
    Connection connection;
    private Logger logger = Logger.getLogger(UserDAO.class);

    public UserDAO() {
        connection = connection();
    }


    public boolean create(User newItem) {

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO test (LDAPLOGIN,USERNAME) VALUES(?, ?);");
            preparedStatement.setString(1, newItem.getLdapLogin());
            preparedStatement.setString(2, newItem.getUserName());
            preparedStatement.execute();

        } catch (SQLException e) {
           logger.error("Error in the function create",e);

        } finally {
            disconnect(connection);
        }

        return true;
    }


    public boolean update(User item) {
        Connection connection;
        User temp = new User(item);

        connection = connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE  test  SET  LDAPLOGIN = ? , USERNAME= ? WHERE ID = ?");
            preparedStatement.setString(2, item.getLdapLogin());
            preparedStatement.setString(3, item.getUserName());
            preparedStatement.setInt(1, item.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Error in the function update",e);

        } finally {
            disconnect(connection);
        }

        return false;
    }


    public boolean delete(User item) {


        connection = connection();

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("DELETE FROM test WHERE ID =  ?"));
            preparedStatement.setInt(1, item.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error in the function delete",e);
        } finally {
            disconnect(connection);
        }

        return false;
    }

    public User load(Long id) {
        connection = connection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT FROM test WHERE ID =  ?"));
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getInt("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error in the function load",e);
        }
        return null;
    }

    public Collection<User> loadAll() {
        connection = connection();
        ArrayList<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT * FROM test"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error in the function loadAll",e);
        }
        return null;
    }


}
