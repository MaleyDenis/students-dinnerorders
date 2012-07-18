package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Role;
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

    private Logger logger = Logger.getLogger(UserDAO.class);

    public UserDAO() {

    }


    public boolean create(User newItem) {
        Connection connection = connection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO user (LDAPLOGIN,USERNAME,ROLE) VALUES(?, ?, ?);");
            preparedStatement.setString(1, newItem.getLdapLogin());
            preparedStatement.setString(2, newItem.getUserName());
            if (newItem.getRole() != null)
                preparedStatement.setString(3, newItem.getRole());
            else
                preparedStatement.setString(3, Role.ADMIN.getValue());
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error("Error in the function create", e);

        } finally {
            disconnect(connection);
        }

        return true;
    }


    public boolean update(User item) {

        Connection connection = connection();
        User temp = new User(item);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE  user  SET  LDAPLOGIN = ? , USERNAME= ?, ROLE= ?  WHERE ID = ?");
            preparedStatement.setString(1, item.getLdapLogin());
            preparedStatement.setString(2, item.getUserName());
            if (item.getRole() != null)
                preparedStatement.setString(3, item.getRole());
            else
                preparedStatement.setString(3, "user");
            preparedStatement.setInt(4, item.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Error in the function update", e);

        } finally {
            disconnect(connection);
        }

        return false;
    }


    public boolean delete(User item) {


        Connection connection = connection();

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("DELETE FROM user WHERE ID =  ?"));
            preparedStatement.setInt(1, item.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error in the function delete", e);
        } finally {
            disconnect(connection);
        }

        return false;
    }

    public User load(Long id) {
        Connection connection = connection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT FROM user WHERE ID =  ?"));
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getInt("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"), resultSet.getString("ROLE"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error in the function load", e);
        }
        return null;
    }

    public Collection<User> loadAll() {
        Connection connection = connection();
        ArrayList<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT * FROM user"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"), resultSet.getString("ROLE"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error in the function loadAll", e);
        }
        return null;
    }


}
