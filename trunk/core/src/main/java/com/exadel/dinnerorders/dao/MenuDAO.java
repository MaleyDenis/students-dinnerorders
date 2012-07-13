package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.sql.SQLException;

/**
 * User: Сенсей
 * Date: 13.7.12
 */

public class MenuDAO extends BaseDAO{
    public boolean create(Object newItem) {
        Connection connection = connection();
        if(connection != null){
            try{
                if(newItem instanceof Menu){
                    Menu newMenu = (Menu)newItem;
                    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement("INSERT INTO menus VALUES(?, ?, ?, ?)");
                    preparedStatement.setLong(1, newMenu.getId());
                    preparedStatement.setString(2, newMenu.getCafeName());
                    preparedStatement.setTimestamp(3, new Timestamp(newMenu.getDateStart().getTime()));
                    preparedStatement.setTimestamp(4,  new Timestamp(newMenu.getDateEnd().getTime()));
                    preparedStatement.executeUpdate();
                    for(List<MenuItem> items : newMenu.getItems().values()){
                        for(MenuItem item : items){
                            preparedStatement = (PreparedStatement)connection.prepareStatement("INSERT INTO menu_menuitem VALUES(?, ?)");
                            preparedStatement.setLong(1, newMenu.getId());
                            preparedStatement.setLong(2, item.getId());
                            preparedStatement.executeUpdate();
                        }
                    }
                }
                if(newItem instanceof MenuItem){
                    MenuItem newMenuItem = (MenuItem)newItem;
                    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement("INSERT INTO menuitem VALUE(?, ?, ?, ?)");
                    preparedStatement.setLong(1, newMenuItem.getId());
                    preparedStatement.setString(2, newMenuItem.getWeekday().name());
                    preparedStatement.setString(3, newMenuItem.getDescription());
                    preparedStatement.setDouble(4, newMenuItem.getCost());
                    preparedStatement.executeUpdate();
                }
            }catch(SQLException e){
                System.out.println("Create: SQLException caught");
                System.out.println("---");
                while ( e != null ){
                    System.out.println("Message   : " + e.getMessage());
                    System.out.println("SQLState  : " + e.getSQLState());
                    System.out.println("ErrorCode : " + e.getErrorCode());
                    System.out.println("---");
                    e = e.getNextException();
                }

            }
            disconnect(connection);
            return true;
        }
        return false;
    }

    public boolean update(Object item) {
        return false;
    }

    public boolean delete(Object item) {
        return false;
    }

    public Object read() {
        return null;
    }
}
