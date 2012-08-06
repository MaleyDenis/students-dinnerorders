package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
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
                preparedStatement.close();

                for (List<MenuItem> items : newItem.getItems().values()) {
                    for (MenuItem item : items) {
                        preparedStatement =  connection.prepareStatement("INSERT INTO menu_menuitem VALUES(?, ?, ?);");
                        preparedStatement.setLong(1, getID());
                        preparedStatement.setLong(2, newItem.getId());
                        preparedStatement.setLong(3, item.getId());
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
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
        if (connection != null) {
            try {
                PreparedStatement preparedStatement =  connection.prepareStatement
                        ("UPDATE menu SET cafename = ?, date_start = ?, date_end = ? WHERE menu_id = ?;");
                preparedStatement.setString(1, item.getCafeName());
                preparedStatement.setTimestamp(2, item.getDateStart());
                preparedStatement.setTimestamp(3, item.getDateEnd());
                preparedStatement.setLong(4, item.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();

                deleteMenuItems(item, connection);
                createNewMenuItems(item, connection);

                for (List<MenuItem> items : item.getItems().values()) {
                    for (MenuItem menuItem : items) {
                        preparedStatement = connection.prepareStatement
                                ("INSERT INTO menu_menuitem (id, menu_id, menuitem_id) VALUES(?, ?, ?);");
                        preparedStatement.setLong(1, getID());
                        preparedStatement.setLong(2, item.getId());
                        preparedStatement.setLong(3, menuItem.getId());
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }
                }
                return true;
            } catch (SQLException e) {
                logger.error("MenuDAO: update has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return false;
    }

    private boolean createNewMenuItems(Menu item, Connection connection) {
        boolean result = false;
        try {
            for (List<MenuItem> items : item.getItems().values()) {
                for (MenuItem menuItem : items) {
                    PreparedStatement preparedStatement = connection.prepareStatement
                            ("INSERT INTO menuitem (menuitem_id, weekday, description, cost) VALUES(?, ?, ?, ?);");
                    menuItem.setId(getID());
                    preparedStatement.setLong(1, menuItem.getId());
                    preparedStatement.setString(2, menuItem.getWeekday().name());
                    preparedStatement.setString(3, menuItem.getDescription());
                    preparedStatement.setDouble(4, menuItem.getCost());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }
            result = true;
        } catch (SQLException e) {
            logger.error("MenuDAO: update has failed.", e);
        }
        return result;
    }

    private void deleteMenuItems(Menu menu, Connection connection) {
        List<MenuItem> allMenuItems = new ArrayList<MenuItem>();
        for (List<MenuItem> dayList : menu.getItems().values()) {
            allMenuItems.addAll(dayList);
        }
        try {
            for (MenuItem item : allMenuItems) {
                if (item.getId() != null) {
                    PreparedStatement statement = connection.prepareStatement
                            ("DELETE FROM dinnerorders.menuitem WHERE menuitem_id = ?");
                    statement.setLong(1, item.getId());
                    statement.executeUpdate();
                    statement.close();
                    item.setId(null);
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM dinnerorders.menu_menuitem WHERE menu_id = ?;");

            preparedStatement.setLong(1, menu.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException sqlException) {
            logger.error("MenuDAO.deleteMenuItems: SQLException", sqlException);
        }
    }

    public boolean delete(Menu item) {
        Connection connection = getConnection(this);
        if (connection != null) {
            try {
                PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM menu WHERE menu_id =?;");
                preparedStatement.setLong(1, item.getId());
                preparedStatement.executeUpdate();
                preparedStatement =  connection.prepareStatement("DELETE FROM menu_menuitem WHERE menu_id = ?;");
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
                menuResultSet.close();
                menuStatement.close();
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
                PreparedStatement menuStatement = connection.prepareStatement("SELECT * FROM menu;");
                ResultSet menuResultSet = menuStatement.executeQuery();
                while (menuResultSet.next()) {
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
                menuResultSet.close();
                menuStatement.close();
            } catch (SQLException e) {
                logger.error("MenuDAO: load all has failed.", e);
            } finally {
                disconnect(connection);
            }
        }
        return menus;
    }
    public Collection<Long> getAllMenuIds() {
        Connection connection = getConnection(this);
        Collection<Long> menusID = Collections.emptyList();
        if (connection != null) {
            try {
                List<Long> allIDs = new ArrayList<Long>();
                PreparedStatement menuStatement =  connection.prepareStatement("SELECT menu_id FROM menu;");
                ResultSet menuResultSet = menuStatement.executeQuery();
                while(menuResultSet.next()){
                    allIDs.add(menuResultSet.getLong(1));
                }
                menusID = allIDs;
            } catch (SQLException e) {
                logger.error("MenuDAO: call id has failed.", e);
            }  finally{
                disconnect(connection);
            }
        }
        return menusID;
    }

    private List<MenuItem> loadMenuItemsList(Connection connection, Long menuId) {
        List<MenuItem> menuItemsList = new ArrayList<MenuItem>();
        try {
            PreparedStatement menuStatement = connection.prepareStatement("SELECT * FROM menu_menuitem WHERE menu_id = ?;");
            menuStatement.setLong(1, menuId);
            ResultSet menuItemsResultSet = menuStatement.executeQuery();

            while(menuItemsResultSet.next()) {
                long menuItemID = menuItemsResultSet.getLong(3);
                MenuItem menuItem = loadMenuItem(menuItemID, connection);
                menuItemsList.add(menuItem);
            }
            menuItemsResultSet.close();
            menuStatement.close();
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
            menuItemResultSet.close();
            menuItemResultSet.close();
            return newMenuItem;
        } catch (SQLException sqlException) {
            logger.error("MenuDAO: loading menuItem failed.", sqlException);
        }
        return newMenuItem;
    }
}