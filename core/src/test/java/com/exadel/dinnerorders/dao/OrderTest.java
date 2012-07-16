package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Order;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Asus
 * Date: 16.07.12
 * Time: 1:47
 * To change this template use File | Settings | File Templates.
 */
public class OrderTest extends TestCase {
    @Test
    public void setUp() throws Exception {

    }

    public void tearDown() throws Exception {

    }

    public void testCreate() throws Exception {

        OrderDAO orderDAO = new OrderDAO();
        Date date = new Date();
        Order order = new Order(new Long(1),new Long(1),23.5,date,date);
        Assert.assertTrue(!order.equals(null));
        assertEquals(order,order);
        assertTrue(orderDAO.create(order));
        Order order1 = new Order(new Long(2),new Long(2),23.5,date,date);
        Assert.assertTrue (!order1.equals(null));;
        assertTrue(orderDAO.create(order1));
        Order order2 = new Order(new Long(3),new Long(3),23.5,date,date);
        Assert.assertTrue (!order2.equals(null));
        assertTrue(orderDAO.create(order2));

    }

    public void testUpdate() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testRead() throws Exception {

    }
}
