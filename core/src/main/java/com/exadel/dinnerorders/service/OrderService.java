package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Order;

import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.List;


public class OrderService {
    Comparator<Order> byDate = new Comparator<Order>() {
        public int compare(final Order p1, final Order p2) {
            return p1.getDateOrder().compareTo(p2.getDateOrder());
        }
    };
    public List<Order> sorted(List<Order> orders){
        Ordering<Order>orderOrdering = Ordering.from(byDate);
        List<Order> orderList = orderOrdering.reverse().sortedCopy(orders);
        return orderList;
    }
}

