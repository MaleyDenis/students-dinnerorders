package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


 public class OrderDAOTest extends TestCase {
    public void testCreate(){
        try {
            OrderDAO orderDAO = new OrderDAO();
        Date date = new Date();

        Order order = new Order(null,(long)1,23.5,date,date);
        Assert.assertNotNull(order);

        MenuItem  menuItem1 = new MenuItem(null, Weekday.MONDAY, "4", (double)1);
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        assertTrue(menuItemDAO.create(menuItem1));
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(menuItem1);
        order.setMenuItemList(menuItems);

        assertTrue(orderDAO.create(order));

        Order order1 = new Order(null,(long)2,23.5,date,date);
        Assert.assertNotNull(order1);

        MenuItem  menuItem2 = new MenuItem((long)3, Weekday.MONDAY, "4", (double)1);
        menuItemDAO.create(menuItem2);
        List<MenuItem> menuItems2 = new ArrayList<MenuItem>();
        menuItems2.add(menuItem2);
        order1.setMenuItemList(menuItems2);
        orderDAO.create(order1);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

   /*public void testUpdate() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        Date date = new Date();
        Order order2 = new Order((long)2,(long)2,23.5,date,date);
        order2.setCost(new Double(54645));
        order2.setDatePayment(date);
        orderDAO.update(order2);
    }


   public void testDelete() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        Date date = new Date();
        Order order = new Order((long)1,(long)2,645.65,date,date);
        assertTrue(orderDAO.delete(order));
        MenuItem  menuItem1 = new MenuItem((long)2, Weekday.MONDAY, "4", (double)1);
        assertTrue(menuItemDAO.delete(menuItem1));
        Order order1 = new Order((long)2,(long)2,new Double(555),date,date);
        assertTrue(orderDAO.delete(order1));
        MenuItem  menuItem2 = new MenuItem((long)3, Weekday.MONDAY, "4", (double)1);
        assertTrue(menuItemDAO.delete(menuItem2));
    }*/
}

