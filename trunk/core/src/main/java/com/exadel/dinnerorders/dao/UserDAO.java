package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultConnection;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
@DbConnection(connectionType=DefaultConnection.class)
public class UserDAO extends BaseDAO<User> {

    private Logger logger = Logger.getLogger(UserDAO.class);


    /**
     * Create new user.
     * New ID will be generated and assigned into current object.
     * Role by default: USER.
     * @param newItem user data for user creation.
     * @return true | false.
     */
    public boolean create(User newItem) {
        Connection connection = connection(this);
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (ID,LDAPLOGIN,USERNAME,ROLE) VALUES(?, ?, ?, ?);");
            newItem.setId(getID());
            preparedStatement.setLong(1, newItem.getId());
            preparedStatement.setString(2, newItem.getLdapLogin());
            preparedStatement.setString(3, newItem.getUserName());

            if (newItem.getRole() != null)
                preparedStatement.setString(4, newItem.getRole().name());
            else
                preparedStatement.setString(4, Role.USER.name());
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error("Error in the function create", e);

        } finally {
            disconnect(connection);
        }

        return true;
    }


    public boolean update(User item) {

        Connection connection = connection(this);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE  user  SET  LDAPLOGIN = ? , USERNAME= ?, ROLE= ?  WHERE ID = ?");
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

        Connection connection = connection(this);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(("DELETE FROM user WHERE ID =  ?"));
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
        Connection connection = connection(this);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(("SELECT * FROM user WHERE ID =  ?;"));
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getLong("ID"), resultSet.getString("LDAPLOGIN"), resultSet.getString("USERNAME"), Role.valueOf(resultSet.getString("ROLE")));
            }
        } catch (SQLException e) {
            logger.error("Error (SQLException in load function!", e);
        } finally {
            disconnect(connection);
        }

        return null;
    }

    public Collection<User> loadAll() {
        Connection connection = connection(this);
        Collection<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(("SELECT * FROM user"));
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

}
