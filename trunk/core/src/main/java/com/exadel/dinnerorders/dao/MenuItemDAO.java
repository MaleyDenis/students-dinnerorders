package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */
public class MenuItemDAO extends BaseDAO<MenuItem> {

    public boolean create(MenuItem newItem) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO menuitems VALUE(?, ?, ?, ?);");
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
                StringBuilder query = new StringBuilder("UPDATE menuitems SET ");
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
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM menuitems WHERE id = '" + item.getId() + "';");
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

    public MenuItem load(Long id) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement menuStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menuitems WHERE id = ?;");
                menuStatement.setLong(1, id);
                ResultSet menuItemResultSet = menuStatement.executeQuery();
                if(menuItemResultSet.next()){
                    Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
                    String description = menuItemResultSet.getString(3);
                    Double cost = menuItemResultSet.getDouble(4);
                    MenuItem newMenuItem = new MenuItem(id, weekday, description, cost);
                    disconnect(connection);
                    return newMenuItem;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return null;
    }

    public Collection<MenuItem> loadAll(){
        Collection<MenuItem> items = new ArrayList<MenuItem>();
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement menuStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menuitems;");
                ResultSet menuItemResultSet = menuStatement.executeQuery();
                while(menuItemResultSet.next()){
                    Long id = menuItemResultSet.getLong(1);
                    Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
                    String description = menuItemResultSet.getString(3);
                    Double cost = menuItemResultSet.getDouble(4);
                    MenuItem newMenuItem = new MenuItem(id, weekday, description, cost);
                    items.add(newMenuItem);
                }
                disconnect(connection);
                return items;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return null;
    }
}
