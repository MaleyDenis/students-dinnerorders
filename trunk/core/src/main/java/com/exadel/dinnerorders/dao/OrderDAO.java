package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Order;

import com.exadel.dinnerorders.entity.Weekday;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class OrderDAO extends BaseDAO<Order> {
    private final Logger logger = Logger.getLogger(OrderDAO.class);

    public boolean create(Order orderItem)  {
        Connection connection = connection();
        try {
            if (connection != null){
                PreparedStatement pst = (PreparedStatement)connection.prepareStatement
                        ("INSERT INTO dinnerorders.order VALUES(?,?,?,?,?)");
                pst.setLong(1, orderItem.getId());
                pst.setLong(2, orderItem.getUserID());
                pst.setDouble(3, orderItem.getCost());
                pst.setTimestamp(4, new java.sql.Timestamp(orderItem.getDateOrder().getTime()));
                pst.setTimestamp(5, new java.sql.Timestamp(orderItem.getDatePayment().getTime()));
                pst.executeUpdate();
                List<MenuItem> list = orderItem.getMenuItemList();
                pst.close();
                for (MenuItem menuItem:list){
                    PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement
                            ("INSERT INTO dinnerorders.order_menuitem (order_id, menu_item_id) VALUE (?,?)");
                    preparedStatement.setLong(1, orderItem.getId());
                    preparedStatement.setLong(2, menuItem.getId());
                    preparedStatement.executeUpdate();
                }
                disconnect(connection);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Create error the method",e);
        } finally {
            disconnect(connection);
        }
        return false;
    }

    public boolean update(Order item) {
        Connection connection = connection();
        Double cost;
        Date datePayment;
        try {
            if (connection != null){
                cost = item.getCost();
                datePayment = item.getDatePayment();
                PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement
                        ("UPDATE dinnerorders.order SET" +" cost = ?,date_payment = ? ");
                preparedStatement.setDouble(1, cost);
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(datePayment.getTime()));
                preparedStatement.executeUpdate();
                preparedStatement.close();
                disconnect(connection);
                return true;
            }
        }
        catch (SQLException e) {
           logger.error("Update error the method",e);
        } finally {
            disconnect(connection);
        }
        return false;
    }

    public boolean delete(Order item) {
        Connection connection = connection();
        try {
            if (connection != null){
                PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement
                        ("DELETE FROM dinnerorders.order WHERE order_id=?");
                preparedStatement.setLong(1,item.getId());
                preparedStatement.executeUpdate();
                deleteFromOrderItemsTable(item);
                preparedStatement.close();
                disconnect(connection);
                return true;
            }
        } catch (SQLException e) {
           logger.error("Delete error the method",e);
        } finally {
            disconnect(connection);
        }
        return false;
    }

    private void deleteFromOrderItemsTable(Order item) {
        Connection connection = connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = (PreparedStatement)connection.prepareStatement
                    ("DELETE FROM dinnerorders.order_menuitem WHERE order_id=?");
            preparedStatement.setLong(1,item.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
           logger.error("DeleteFromOrderItemsTable error the method",e);
        } finally {
            disconnect(connection);
        }
    }

    public Collection<Order> loadAll() {
        Order order = null;
        List<Order> orders = new ArrayList<Order>();
        List<MenuItem> menuItems =null;
        Connection connection = connection();
        try {
            Statement statement = (Statement)connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dinnerorders.order");
            while (resultSet.next()){
                order = new Order(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getDouble(3),
                        resultSet.getDate(4),
                        resultSet.getDate(5));

                orders.add(order);
                menuItems = getOrderMenuItem(order,connection);
                order.setMenuItemList(menuItems);
            }
        } catch (SQLException e) {
           logger.error("LoadAll error the method",e);
        } finally {
            disconnect(connection);
        }
        return orders;
    }

    private List<MenuItem> getOrderMenuItem(Order order, Connection connection) {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List <Long> menuItemID = new ArrayList<Long>();
        try {
            Statement statement = (Statement)connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("SELECT * FROM dinnerorders.order_menuitem WHERE order_id = " + order.getId());
            while (resultSet.next()){
                menuItemID.add(resultSet.getLong(2));
            }
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement
                    ("SELECT * FROM dinnerorders.menuitem");
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()){
                if (menuItemID.contains(resultSet1.getLong(1))){
                    MenuItem menuItem = new MenuItem(resultSet.getLong(1),
                            Weekday.valueOf(resultSet1.getString(2)), resultSet1.getString(3),resultSet1.getDouble(4));
                    menuItems.add(menuItem);
                }
            }
        } catch (SQLException e) {
            logger.error("GetOrderMenuItem error the method",e);
        } finally {
            disconnect(connection);
        }
        return menuItems;
    }

    public Order load(Long id) {
        Connection connection = connection();
        Order order = null;
        try {
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement
                    ("SELECT * FROM dinnerorders.order WHERE order_id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
            order = new Order(resultSet.getLong(1),resultSet.getLong(2),resultSet.getDouble(3),
                    resultSet.getDate(4),resultSet.getDate(5));
            }
            order.setMenuItemList(getOrderMenuItem(order, connection));
        } catch (SQLException e) {
           logger.error("Load error the method",e);
        } finally {
            disconnect(connection);
        }
        return  order;
    }
}
