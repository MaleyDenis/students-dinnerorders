package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.*;
import java.util.*;

@DbConnection(connectionType=DefaultMysqlConnectionProvider.class)
public class OrderDAO extends BaseDAO<Order> {
    private final Logger logger = Logger.getLogger(OrderDAO.class);

    public boolean create(Order orderItem)  {
        Session session = openSession();
        try{
            if(session != null) {
                orderItem.setId(getID());
                session.beginTransaction();
                session.save(orderItem);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public boolean update(Order item) {
        Session session = openSession();
        try{
            if(session != null) {
                session.beginTransaction();
                session.update(item);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public boolean delete(Order item)  {
        Session session = openSession();
        try{
            if(session != null) {
                session.beginTransaction();
                session.delete(item);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public Collection<Order> loadAll()  {
        List<Order> items = new ArrayList<Order>();
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                Query query = session.createQuery("select order from Order order order by order.id");
                items = query.list();
                session.getTransaction().commit();
                if (items != null) {
                    return items;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return Collections.emptyList();
    }

    private List<MenuItem> getOrderMenuItem(Order order, Connection connection) {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List <Long> menuItemID = new ArrayList<Long>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("SELECT * FROM dinnerorders.order_menuitem WHERE order_id = " + order.getId());
            while (resultSet.next()){
                menuItemID.add(resultSet.getLong(3));
            }
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM dinnerorders.menuitem");
            ResultSet resultSet1 = preparedStatement.executeQuery();
            while (resultSet1.next()){
                if (menuItemID.contains(resultSet1.getLong(1))){
                    MenuItem menuItem = new MenuItem(resultSet1.getLong(1),
                            Weekday.valueOf(resultSet1.getString(2)), resultSet1.getString(3),resultSet1.getDouble(4));
                    menuItems.add(menuItem);
                }
            }
        } catch (SQLException e) {
            logger.error("GetOrderMenuItem error the method",e);
        }
        return menuItems;
    }

    public Order load(Long id)   {
        Connection connection = getConnection(this);
        Order order = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM dinnerorders.order WHERE order_id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
            order = new Order(resultSet.getLong(1),resultSet.getLong(2),resultSet.getDouble(3),
                    resultSet.getDate(4),resultSet.getDate(5));
            }
            if (order != null) {
                order.setMenuItemList(getOrderMenuItem(order, connection));
            }
        } catch (SQLException e) {
           logger.error("Load error the method",e);
        } finally {
            disconnect(connection);
        }
        return  order;
    }
}
