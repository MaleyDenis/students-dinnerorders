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

    public boolean create(User newItem) {
        Connection connection = connection();
        try {
            PreparedStatement preparedStatement =
                    preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO user (LDAPLOGIN,USERNAME,ROLE) VALUES(?, ?, ?);");

            preparedStatement.setString(1, newItem.getLdapLogin());
            preparedStatement.setString(2, newItem.getUserName());
            if (newItem.getRole() != null)

                preparedStatement.setString(3, newItem.getRole().name());
            else
                preparedStatement.setString(3, Role.ADMIN.name());
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE  user  SET  LDAPLOGIN = ? , USERNAME= ?, ROLE= ?  WHERE ID = ?");
            preparedStatement.setString(1, item.getLdapLogin());
            preparedStatement.setString(2, item.getUserName());
            if (item.getRole() != null)
                preparedStatement.setString(3, item.getRole().name());
            else
                preparedStatement.setString(3, Role.USER.name());

            preparedStatement.setLong(4, item.getId());

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
            preparedStatement.setLong(1, item.getId());
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
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT * FROM user WHERE ID =  ?;"));
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getLong("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"), Role.valueOf(resultSet.getString("ROLE")));
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error (SQLException in load function!", e);
        } finally {
            disconnect(connection);
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
                User user = new User(resultSet.getLong("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"), Role.valueOf(resultSet.getString("ROLE")));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error in the function loadAll", e);
        } finally {
            disconnect(connection);
        }

        return null;
    }

    public boolean dropTable() {
        Connection connection = connection();

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("DELETE FROM user WHERE id>0"));
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error("Error in the  drop  function", e);
            return false;
        } finally {
            disconnect(connection);
        }
        return true;
    }

    public Long getMaxIndex() {
        Long maxIndex ;
        Connection connection = connection();

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(("SELECT MAX(ID) FROM (user)"));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
            maxIndex=new Long(resultSet.getInt("MAX(ID)") );
                return maxIndex;
            }


        } catch (SQLException e) {
            logger.error("Error! Don't get max value of index", e);
            return null;
        } finally {
            disconnect(connection);
        }

     return  null;
    }

}