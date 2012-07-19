package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */

public class MenuItemDAO extends BaseDAO<MenuItem> {
    private Logger logger = Logger.getLogger(MenuItemDAO.class);

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
                return true;
            } catch (SQLException e) {
                logger.error("MenuItemDAO: create has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return false;
    }

    public boolean update(MenuItem item) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE menuitem SET weekday = ?, description = ?, cost = ? WHERE menuitem_id = ?;");
                preparedStatement.setString(1, item.getWeekday().name());
                preparedStatement.setString(2, item.getDescription());
                preparedStatement.setDouble(3, item.getCost());
                preparedStatement.setLong(4, item.getId());
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.error("MenuItemDAO: update has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return false;
    }

    public boolean delete(MenuItem item) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM menuitem WHERE menuitem_id = ?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.error("MenuItemDAO: delete has failed.", e);
            } finally{
                disconnect(connection);
            }
        }
        return false;
    }

    public MenuItem load(Long id) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement menuStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menuitem WHERE menuitem_id = ?;");
                menuStatement.setLong(1, id);
                ResultSet menuItemResultSet = menuStatement.executeQuery();
                if(menuItemResultSet.next()){
                    Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
                    String description = menuItemResultSet.getString(3);
                    Double cost = menuItemResultSet.getDouble(4);
                    return new MenuItem(id, weekday, description, cost);
                }
            } catch (SQLException e) {
                logger.error("MenuItemDAO: load has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return null;
    }

    public Collection<MenuItem> loadAll(){
        Collection<MenuItem> items = new ArrayList<MenuItem>();
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement menuStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menuitem;");
                ResultSet menuItemResultSet = menuStatement.executeQuery();
                while(menuItemResultSet.next()){
                    Long id = menuItemResultSet.getLong(1);
                    Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
                    String description = menuItemResultSet.getString(3);
                    Double cost = menuItemResultSet.getDouble(4);
                    MenuItem newMenuItem = new MenuItem(id, weekday, description, cost);
                    items.add(newMenuItem);
                }
                return items;
            } catch (SQLException e) {
                logger.error("MenuItemDAO: load all has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return null;
    }
}
