package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */
@DbConnection(connectionType=DefaultMysqlConnectionProvider.class)
public class MenuItemDAO extends BaseDAO<MenuItem> {
    private Logger logger = Logger.getLogger(MenuItemDAO.class);

    public boolean create(MenuItem newItem)  {
        Connection connection = getConnection(this);
        if (connection != null && newItem.getId() == null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO menuitem VALUE(?, ?, ?, ?);");
                newItem.setId(getID());
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

    public boolean update(MenuItem item)  {
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE menuitem SET weekday = ?, description = ?, cost = ? WHERE menuitem_id = ?;");
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
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM menuitem WHERE menuitem_id = ?;");
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

    public MenuItem load(Long id)  {
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement menuStatement =  connection.prepareStatement("SELECT * FROM menuitem WHERE menuitem_id = ?;");
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

    public Collection<MenuItem> loadAll()   {
        Collection<MenuItem> items = new ArrayList<MenuItem>();
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement menuStatement =  connection.prepareStatement("SELECT * FROM menuitem;");
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
