package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Order;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;

/**
 * * User: Denis
 * */

 public class OrderTest extends TestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        Date date = new Date();
        Order order = new Order((long)1,(long)1,23.5,date,date);
        Assert.assertNotNull(order);
        //assertEquals(order, order);
        assertTrue(orderDAO.create(order));

        Order order1 = new Order((long)2,(long)2,23.5,date,date);
        Assert.assertNotNull(order1);
        assertTrue(orderDAO.create(order1));

        Order order2 = new Order((long)3,(long)3,23.5,date,date);
        Assert.assertNotNull(order2);
        assertTrue(orderDAO.create(order2));
    }

    @Test
    public void testUpdate() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        Date date = new Date();
        Order order2 = new Order((long)4,(long)3,23.5,date,date);
        orderDAO.create(order2);
        order2.setCost(54645);
        order2.setDatePayment(date);
        orderDAO.update(order2);
    }

    @Test
    public void  testLoadAll() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        Collection<Order> collection = orderDAO.loadAll();
        for (Order order : collection) {
            System.out.println(order.getCost());
        }

    }

    @Test
    public void testDelete() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        Date date = new Date();
        Order order = new Order((long)1,(long)2,645.65,date,date);
        assertTrue(orderDAO.delete(order));
        Order order1 = new Order((long)2,(long)2,555,date,date);
        assertTrue(orderDAO.delete(order1));
        Order order2 = new Order((long)3,(long)2,645.65,date,date);
        assertTrue(orderDAO.delete(order2));
        Order order3 = new Order((long)4,(long)2,645.65,date,date);
        assertTrue(orderDAO.delete(order3));
    }

    @Test
    public void testLoad() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        System.out.println(orderDAO.load((long)6).getCost());
    }

}

