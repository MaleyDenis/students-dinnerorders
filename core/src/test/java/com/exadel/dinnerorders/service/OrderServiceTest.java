package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Order;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Collection;



public class OrderServiceTest extends TestCase {
    @Test
    public void testSortedALL() throws Exception {

        Collection<Order> collection = OrderService.sortedAll();
        for (Order order: collection){
            assertNotNull(order.getDateOrder());
        }
    }

}
