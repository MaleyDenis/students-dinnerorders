package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.SQLException;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */
public class MenuItemDAO extends BaseDAO<MenuItem> {

    public boolean create(MenuItem newItem) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO menuitem VALUE(?, ?, ?, ?);");
                preparedStatement.setLong(1, newItem.getId());
                preparedStatement.setString(2, newItem.getWeekday().name());
                preparedStatement.setString(3, newItem.getDescription());
                preparedStatement.setDouble(4, newItem.getCost());
                preparedStatement.executeUpdate();
                disconnect(connection);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return false;
    }

    public boolean update(MenuItem item) {
        Connection connection = connection();
        if (connection != null) {
            try {
                StringBuilder query = new StringBuilder("UPDATE menuitem SET ");
                query.append("weekday = '").append(item.getWeekday().name()).append("', description = '");
                query.append(item.getDescription()).append("', cost = '").append(item.getCost()).append("' WHERE");
                query.append(" id = '").append(item.getId()).append("';");
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query.toString());
                preparedStatement.executeUpdate();
                disconnect(connection);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return false;
    }

    public boolean delete(MenuItem item) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM menuitem WHERE id = '" + item.getId() + "';");
                preparedStatement.executeUpdate();
                disconnect(connection);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return false;
    }

    public MenuItem read() {
        return null;
    }
}
