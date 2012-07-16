package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.util.List;
import java.sql.SQLException;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

public class MenuDAO extends BaseDAO<Menu>{
    public boolean create(Menu newItem){
        Connection connection = connection();
        if(connection != null){
            try{
                PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement("INSERT INTO menus VALUES(?, ?, ?, ?);");
                preparedStatement.setLong(1, newItem.getId());
                preparedStatement.setString(2, newItem.getCafeName());
                preparedStatement.setTimestamp(3, newItem.getDateStart());
                preparedStatement.setTimestamp(4, newItem.getDateEnd());
                preparedStatement.executeUpdate();
                for(List<MenuItem> items : newItem.getItems().values()){
                    for(MenuItem item : items){
                        preparedStatement = (PreparedStatement)connection.prepareStatement("INSERT INTO menu_menuitem VALUES(?, ?);");
                        preparedStatement.setLong(1, newItem.getId());
                        preparedStatement.setLong(2, item.getId());
                        preparedStatement.executeUpdate();
                    }
                }
                disconnect(connection);
                return true;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return false;
    }

    public boolean update(Menu item){
        Connection connection = connection();
        if(connection != null){
            try{
                StringBuilder query = new StringBuilder("UPDATE menus SET ");
                query.append("cafename = '").append(item.getCafeName()).append("', datestart = '").append(item.getDateStart());
                query.append("', dateend = '").append(item.getDateEnd()).append("' WHERE").append(" id = '").append(item.getId());
                query.append("';");
                PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(query.toString());
                preparedStatement.executeUpdate();
                query = new StringBuilder("DELETE FROM menu_menuitem WHERE idmenu = \"");
                query.append(item.getId()).append("\";");
                preparedStatement = (PreparedStatement)connection.prepareStatement(query.toString());
                preparedStatement.executeUpdate();
                for(List<MenuItem> items : item.getItems().values()){
                    for(MenuItem menuItem : items){
                        preparedStatement = (PreparedStatement)connection.prepareStatement("INSERT INTO menu_menuitem VALUES(?, ?);");
                        preparedStatement.setLong(1, item.getId());
                        preparedStatement.setLong(2, menuItem.getId());
                        preparedStatement.executeUpdate();
                    }
                }
                disconnect(connection);
                return true;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return false;
    }

    public boolean delete(Menu item){
        Connection connection = connection();
        if(connection != null){
            try{
                PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement("DELETE FROM menus WHERE id = '" + item.getId() +"';");
                preparedStatement.executeUpdate();
                preparedStatement = (PreparedStatement)connection.prepareStatement("DELETE FROM menu_menuitem WHERE idmenu = '" + item.getId() + "';");
                preparedStatement.executeUpdate();
                disconnect(connection);
                return true;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        disconnect(connection);
        return false;
    }

    public Menu read() {
        return null;
    }
}