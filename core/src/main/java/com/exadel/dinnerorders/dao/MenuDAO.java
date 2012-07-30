package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.*;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@DbConnection(connectionType=DefaultMysqlConnectionProvider.class)
public class MenuDAO extends BaseDAO<Menu> {
    private Logger logger = Logger.getLogger(MenuDAO.class);

    public boolean create(Menu newItem) {
        Connection connection = getConnection(this);
        if (connection != null && newItem.getId() == null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO menu VALUES(?, ?, ?, ?);");
                newItem.setId(getID());
                preparedStatement.setLong(1, newItem.getId());
                preparedStatement.setString(2, newItem.getCafeName());
                preparedStatement.setTimestamp(3, newItem.getDateStart());
                preparedStatement.setTimestamp(4, newItem.getDateEnd());
                preparedStatement.executeUpdate();

                for (List<MenuItem> items : newItem.getItems().values()) {
                    for (MenuItem item : items) {
                        preparedStatement =  connection.prepareStatement("INSERT INTO menu_menuitem VALUES(?, ?, ?);");
                        preparedStatement.setLong(1, getID());
                        preparedStatement.setLong(2, newItem.getId());
                        preparedStatement.setLong(3, item.getId());
                        preparedStatement.executeUpdate();
                    }
                }
                return true;
            } catch (SQLException e) {
                logger.error("MenuDAO: create has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return false;
    }

    public boolean update(Menu item) {
        Connection connection = getConnection(this);
        boolean result = false;
        if (connection != null) {
            try {
                PreparedStatement preparedStatement =  connection.prepareStatement("UPDATE menu SET cafename = ?, date_start = ?, date_end = ? WHERE menu_id = ?;");
                preparedStatement.setString(1, item.getCafeName());
                preparedStatement.setTimestamp(2, item.getDateStart());
                preparedStatement.setTimestamp(3, item.getDateEnd());
                preparedStatement.setLong(4, item.getId());
                preparedStatement.executeUpdate();
                updateMenuItems(connection, item);
                result = true;
            } catch (SQLException e) {
                logger.error("MenuDAO: update has failed.", e);
                return result;
            }  finally{
                disconnect(connection);
            }
        }
        return result;
    }

    public boolean delete(Menu item) {
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM menu WHERE menu_id =?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement("DELETE FROM menu_menuitem WHERE menu_id = ?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.error("MenuDAO: delete has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return false;
    }

    public Menu load(Long id){
        Menu menu = null;
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement menuStatement =  connection.prepareStatement("SELECT * FROM menu WHERE menu_id = ?;");
                menuStatement.setLong(1, id);
                ResultSet menuResultSet = menuStatement.executeQuery();

                if(menuResultSet.next()) {
                    String cafeName = menuResultSet.getString(2);
                    Timestamp dateStart = menuResultSet.getTimestamp(3);
                    Timestamp dateEnd = menuResultSet.getTimestamp(4);
                    menu = new Menu(id, cafeName, dateStart, dateEnd, new HashMap<Weekday, List<MenuItem>>());

                    List<MenuItem> menuItemsList = loadMenuItemsList(connection, id);
                    for (MenuItem menuItem : menuItemsList) {
                        menu.addItem(menuItem);
                    }
                }
            } catch (SQLException e) {
                logger.error("MenuDAO: load has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return menu;
    }

    public Collection<Menu> loadAll() {
        Connection connection = getConnection(this);
        Collection<Menu> menus = new ArrayList<Menu>();
        if (connection != null) {
            try {
                PreparedStatement menuStatement =  connection.prepareStatement("SELECT * FROM menu;");
                ResultSet menuResultSet = menuStatement.executeQuery();
                while(menuResultSet.next()) {
                    Long menuId = menuResultSet.getLong(1);
                    String cafeName = menuResultSet.getString(2);
                    Timestamp dateStart = menuResultSet.getTimestamp(3);
                    Timestamp dateEnd = menuResultSet.getTimestamp(4);
                    Menu newMenu = new Menu(menuId, cafeName, dateStart, dateEnd, new HashMap<Weekday, List<MenuItem>>());

                    List<MenuItem> menuItemsList = loadMenuItemsList(connection, menuId);
                    for (MenuItem menuItem : menuItemsList) {
                        newMenu.addItem(menuItem);
                    }
                    menus.add(newMenu);
                }
            } catch (SQLException e) {
                logger.error("MenuDAO: load all has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return menus;
    }

    private List<MenuItem> loadMenuItemsList(Connection connection, Long menuId) {
        List<MenuItem> menuItemsList = new ArrayList<MenuItem>();
        try {
            PreparedStatement menuStatement =  connection.prepareStatement("SELECT * FROM menu_menuitem WHERE menu_id = ?;");
            menuStatement.setLong(1, menuId);
            ResultSet menuItemsResultSet = menuStatement.executeQuery();

            while(menuItemsResultSet.next()) {
                long menuItemID = menuItemsResultSet.getLong(3);
                MenuItem menuItem = loadMenuItem(menuItemID, connection);
                menuItemsList.add(menuItem);
            }
        } catch (SQLException e) {
            logger.error("MenuDAO: loading list of menu items failed.", e);
        }
        return menuItemsList;
    }

    private MenuItem loadMenuItem(long menuItemId, Connection connection) {
        MenuItem newMenuItem = null;
        try {
            PreparedStatement menuItemStatement =  connection.prepareStatement("SELECT * FROM menuitem WHERE menuitem_id = ?;");
            menuItemStatement.setLong(1, menuItemId);
            ResultSet menuItemResultSet = menuItemStatement.executeQuery();
            menuItemResultSet.next();
            Weekday weekday = Weekday.valueOf(menuItemResultSet.getString(2));
            String description = menuItemResultSet.getString(3);
            Double cost = menuItemResultSet.getDouble(4);
            newMenuItem = new MenuItem(menuItemId, weekday, description, cost);
            return newMenuItem;
        } catch (SQLException sqlException) {
            logger.error("MenuDAO: loading menuItem failed.", sqlException);
        }
        return newMenuItem;
    }


    private void updateMenuItems(Connection connection, Menu menu) {
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        try {
            PreparedStatement preparedStatement;
            for (List<MenuItem> items : menu.getItems().values()) {
                for (MenuItem menuItem : items) {
                    if (menuItem.getId() == null) {
                        menuItemDAO.create(menuItem);
                        preparedStatement =  connection.prepareStatement("INSERT INTO menu_menuitem VALUES(?, ?, ?);");
                        preparedStatement.setLong(1, getID());
                        preparedStatement.setLong(2, menu.getId());
                        preparedStatement.setLong(3, menuItem.getId());
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.error("MenuDAO: SQLException while updating menu items", sqlException);
        }
    }
}