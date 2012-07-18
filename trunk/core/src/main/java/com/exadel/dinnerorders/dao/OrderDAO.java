package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Order;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class OrderDAO extends BaseDAO<Order> {

    public boolean create(Order newItem)  {
        Connection connection = connection();
        try {
            if (connection != null){
                PreparedStatement pst = (PreparedStatement)connection.prepareStatement
                        ("INSERT INTO dinnerorders.order VALUES(?,?,?,?,?)");
                pst.setLong(1,newItem.getId());
                pst.setLong(2,newItem.getUserID());
                pst.setDouble(3, newItem.getCost());
                pst.setTimestamp(4, new java.sql.Timestamp(newItem.getDateOrder().getTime()));
                pst.setTimestamp(5, new java.sql.Timestamp(newItem.getDatePayment().getTime()));
                pst.executeUpdate();

                pst.close();
                disconnect(connection);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                preparedStatement.close();
                disconnect(connection);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Order> loadAll() {
        Order order;
        List<Order> orders = new ArrayList<Order>();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  order;
    }
}
