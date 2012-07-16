package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Asus
 * */
public class OrderDAO extends BaseDAO<Order> {
    public boolean create(Order newItem)  {
        Connection connection = connection();
        try {
            if (connection != null){
                PreparedStatement pst = connection.prepareStatement("INSERT INTO orderv15 VALUES(?,?,?,?,?)");
                pst.setLong(1,newItem.getId());
                pst.setLong(2,newItem.getUserID());
                pst.setDouble(3, newItem.getCost());
                pst.setTimestamp(4, new java.sql.Timestamp(newItem.getDateOrder().getTime()));
                pst.setTimestamp(5,new java.sql.Timestamp(newItem.getDatePayment().getTime()));
                pst.executeUpdate();
                pst.close();
                return true;
            }
            disconnect(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(Order item) {
        return false;
    }
    public boolean delete(Order item) {
        Connection connection = connection();
        try {
            if (connection != null){
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM orderv15 WHERE id=?");
                preparedStatement.setLong(1,item.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            }
            disconnect(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Order read() {
        Connection connection = connection();
        return null;
    }
}
