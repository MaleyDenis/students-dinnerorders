package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class OrderDAOTest {
    private static OrderDAO orderDAO;
    private static Order order1;
    private static Order order2;
    private static MenuItem menuItem1;
    private static MenuItem menuItem2;
    private static MenuItem menuItem3;
    private static MenuItemDAO menuItemDAO;

    @Before
    public void setUp() {
        double cost = 50;
        orderDAO = new OrderDAO();
        menuItemDAO = new MenuItemDAO();
        Timestamp dateOrder = new Timestamp(System.currentTimeMillis());
        Timestamp datePayment = new Timestamp(System.currentTimeMillis());

        order1 = new Order(null, orderDAO.getID(), cost, dateOrder, datePayment);
        menuItem1 = new MenuItem(null, Weekday.MONDAY, "OrderItem1", 50d);
        Assert.assertTrue(menuItemDAO.create(menuItem1));
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(menuItem1);
        order1.setMenuItemList(menuItems);

        order2 = new Order(null, orderDAO.getID(), cost, dateOrder, datePayment);
        menuItem2 = new MenuItem(null, Weekday.TUESDAY, "OrderItem2", 50d);
        Assert.assertTrue(menuItemDAO.create(menuItem2));
        menuItems = new ArrayList<MenuItem>();
        menuItems.add(menuItem2);
        order2.setMenuItemList(menuItems);

        menuItem3 = new MenuItem(null, Weekday.WEDNESDAY, "OrderItem3", 50d);
        Assert.assertTrue(menuItemDAO.create(menuItem3));

        Assert.assertTrue(orderDAO.create(order1));
        Assert.assertTrue(orderDAO.create(order2));
    }

    @Test
    public void testCreate() {
        Assert.assertTrue(orderDAO.delete(order1));
        Assert.assertTrue(orderDAO.delete(order2));
        Assert.assertNotNull(order1);
        order1.setId(null);
        Assert.assertTrue(orderDAO.create(order1));
        order2.setId(null);
        Assert.assertNotNull(order2);
        Assert.assertTrue(orderDAO.create(order2));
    }

    @Test
    public void testUpdate() {
        Timestamp newOrderDate = new Timestamp(System.currentTimeMillis());
        newOrderDate.setNanos(0);
        Timestamp newPaymentDate = new Timestamp(System.currentTimeMillis());
        newPaymentDate.setNanos(0);

        double newCost = 100;

        Order updatedOrder = new Order(order1.getId(), order1.getUserID(), newCost, newOrderDate, newPaymentDate);
        List<MenuItem> itemsList = new ArrayList<MenuItem>();
        itemsList.addAll(order1.getMenuItemList());
        itemsList.add(menuItem3);
        updatedOrder.setMenuItemList(itemsList);

        orderDAO.update(updatedOrder);
        Order loaded = orderDAO.load(updatedOrder.getId());
        Assert.assertNotNull(loaded);
        Assert.assertTrue(loaded.getId().equals(updatedOrder.getId()));
        Assert.assertTrue(loaded.getUserID().equals(updatedOrder.getUserID()));
        Assert.assertTrue(loaded.getCost() == updatedOrder.getCost());
        Assert.assertTrue(loaded.getDateOrder().equals(updatedOrder.getDateOrder()));
        Assert.assertTrue(loaded.getDatePayment().equals(updatedOrder.getDatePayment()));
        Assert.assertTrue(isItemsIdentical(loaded.getMenuItemList(), updatedOrder.getMenuItemList()));
        order1 = updatedOrder;
    }

    private boolean isItemsIdentical(List<MenuItem> menuItemList, List<MenuItem> menuItemList1) {
        boolean isOneEquals = false;
        for (MenuItem menuItem : menuItemList) {
            for(MenuItem menuItem2: menuItemList1) {
                if (menuItem.equals(menuItem2)) {
                    isOneEquals = true;
                    break;
                }
            }
            if (!isOneEquals) {
                return false;
            }
            isOneEquals = false;
        }
        return true;
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(orderDAO.delete(order1));
        Assert.assertTrue(menuItemDAO.delete(menuItem1));
        Assert.assertTrue(orderDAO.delete(order2));
        Assert.assertTrue(menuItemDAO.delete(menuItem2));
        Assert.assertTrue(menuItemDAO.delete(menuItem3));
    }

    @After
    public void tearDown() {
        orderDAO.delete(order1);
        orderDAO.delete(order2);
        menuItemDAO.delete(menuItem1);
        menuItemDAO.delete(menuItem2);
        menuItemDAO.delete(menuItem3);
    }
}

