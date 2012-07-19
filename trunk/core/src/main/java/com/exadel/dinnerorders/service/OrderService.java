package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.OrderDAO;
import com.exadel.dinnerorders.entity.Order;
import com.google.common.collect.Ordering;


import java.util.Collection;
import java.util.Comparator;


public class OrderService {
    public static  Comparator<Order> byDate = new Comparator<Order>() {
        public int compare(final Order p1, final Order p2) {
            return p1.getDateOrder().compareTo(p2.getDateOrder());
        }
    };
    public static Collection<Order> sorted(Collection<Order> orders){
        Ordering<Order> orderOrdering = Ordering.from(byDate);
        return orderOrdering.reverse().sortedCopy(orders);
    }
    public static Collection<Order>sortedAll(){
        OrderDAO orderDAO = new OrderDAO();
        Collection<Order> ordersSorted = orderDAO.loadAll();
        if (ordersSorted !=null){
            sorted(ordersSorted);
        }
        return ordersSorted;
    }
}

