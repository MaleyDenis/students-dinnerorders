package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

public class MenuDAO extends BaseDAO<Menu> {
    private Logger logger = Logger.getLogger(BaseDAO.class);

    public boolean create(Menu newItem) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO menu VALUES(?, ?, ?, ?);");
                preparedStatement.setLong(1, newItem.getId());
                preparedStatement.setString(2, newItem.getCafeName());
                preparedStatement.setTimestamp(3, newItem.getDateStart());
                preparedStatement.setTimestamp(4, newItem.getDateEnd());
                preparedStatement.executeUpdate();
                for (List<MenuItem> items : newItem.getItems().values()) {
                    for (MenuItem item : items) {
                        preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO menu_menuitem(menu_id, menuitem_id)  VALUES(?, ?);");
                        preparedStatement.setLong(1, newItem.getId());
                        preparedStatement.setLong(2, item.getId());
                        preparedStatement.executeUpdate();
                    }
                }
                disconnect(connection);
                return true;
            } catch (SQLException e) {
                logger.error("MenuDAO: create has failed.", e);
            }
        }
        disconnect(connection);
        return false;
    }

    public boolean update(Menu item) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE menu SET cafename = ?, date_start = ?, date_end = ? WHERE menu_id = ?;");
                preparedStatement.setString(1, item.getCafeName());
                preparedStatement.setTimestamp(2, item.getDateStart());
                preparedStatement.setTimestamp(3, item.getDateEnd());
                preparedStatement.setLong(4, item.getId());
                preparedStatement.executeUpdate();
                preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM menu_menuitem WHERE menu_id = ?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                for (List<MenuItem> items : item.getItems().values()) {
                    for (MenuItem menuItem : items) {
                        preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO menu_menuitem(menu_id, menuitem_id)  VALUES(?, ?);");
                        preparedStatement.setLong(1, item.getId());
                        preparedStatement.setLong(2, menuItem.getId());
                        preparedStatement.executeUpdate();
                    }
                }
                disconnect(connection);
                return true;
            } catch (SQLException e) {
                logger.error("MenuDAO: update has failed.", e);
            }
        }
        disconnect(connection);
        return false;
    }

    public boolean delete(Menu item) {
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM menu WHERE menu_id =?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM menu_menuitem WHERE menu_id = ?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                disconnect(connection);
                return true;
            } catch (SQLException e) {
                logger.error("MenuDAO: delete has failed.", e);
            }
        }
        disconnect(connection);
        return false;
    }

    public Menu load(Long id){
        Connection connection = connection();
        if (connection != null) {
            try {
                PreparedStatement menuStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menu WHERE menu_id = ?;");
                menuStatement.setLong(1, id);
                ResultSet menuResultSet = menuStatement.executeQuery();
                if(menuResultSet.next()){
                    Long menuId = menuResultSet.getLong(1);
                    String cafeName = menuResultSet.getString(2);
                    Timestamp dateStart = menuResultSet.getTimestamp(3);
                    Timestamp dateEnd = menuResultSet.getTimestamp(4);
                    Menu newMenu = new Menu(menuId, cafeName, dateStart, dateEnd, new HashMap<Weekday, List<MenuItem>>());
                    PreparedStatement itemsIdStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menu_menuitem WHERE menu_id = ?;");
                    itemsIdStatement.setLong(1, menuId);
                    ResultSet itemsIdResultSet = itemsIdStatement.executeQuery();
                    while(itemsIdResultSet.next()){
                        PreparedStatement menuItemStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menuitem WHERE menuitem_id = ?;");
                        menuItemStatement.setLong(1, itemsIdResultSet.getLong(2));
                        ResultSet menuItemResultSet = menuItemStatement.executeQuery();
                        while(menuItemResultSet.next()){
                            Long itemId = menuItemResultSet.getLong(1);
                            Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
                            String description = menuItemResultSet.getString(3);
                            Double cost = menuItemResultSet.getDouble(4);
                            MenuItem newMenuItem = new MenuItem(itemId, weekday, description, cost);
                            newMenu.addItem(newMenuItem);
                        }
                    }
                    disconnect(connection);
                    return newMenu;
                }
            } catch (SQLException e) {
                logger.error("MenuDAO: load has failed.", e);
            }
        }
        disconnect(connection);
        return null;
    }

    public Collection<Menu> loadAll(){
        Connection connection = connection();
        if (connection != null) {
            try {
                Collection<Menu> menus = new ArrayList<Menu>();
                PreparedStatement menuStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menu;");
                ResultSet menuResultSet = menuStatement.executeQuery();
                while(menuResultSet.next()){
                    Long menuId = menuResultSet.getLong(1);
                    String cafeName = menuResultSet.getString(2);
                    Timestamp dateStart = menuResultSet.getTimestamp(3);
                    Timestamp dateEnd = menuResultSet.getTimestamp(4);
                    Menu newMenu = new Menu(menuId, cafeName, dateStart, dateEnd, new HashMap<Weekday, List<MenuItem>>());
                    PreparedStatement itemsIdStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menu_menuitem WHERE menu_id = ?;");
                    itemsIdStatement.setLong(1, menuId);
                    ResultSet itemsIdResultSet = itemsIdStatement.executeQuery();
                    while(itemsIdResultSet.next()){
                        PreparedStatement menuItemStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM menuitem WHERE menuitem_id = ?;");
                        menuItemStatement.setLong(1, itemsIdResultSet.getLong(2));
                        ResultSet menuItemResultSet = menuItemStatement.executeQuery();
                        while(menuItemResultSet.next()){
                            Long itemId = menuItemResultSet.getLong(1);
                            Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
                            String description = menuItemResultSet.getString(3);
                            Double cost = menuItemResultSet.getDouble(4);
                            MenuItem newMenuItem = new MenuItem(itemId, weekday, description, cost);
                            newMenu.addItem(newMenuItem);
                        }
                    }
                    menus.add(newMenu);
                }
                disconnect(connection);
                return menus;
            } catch (SQLException e) {
                logger.error("MenuDAO: load all has failed.", e);
            }
        }
        disconnect(connection);
        return null;
    }
}