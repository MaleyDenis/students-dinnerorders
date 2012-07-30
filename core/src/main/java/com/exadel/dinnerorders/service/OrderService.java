package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.OrderDAO;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.exception.WorkflowException;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;


public class OrderService {

    private static OrderDAO orderDAO = new OrderDAO();

    public static Comparator<Order> byDate = new Comparator<Order>() {
        public int compare(final Order p1, final Order p2) {
            return p1.getDateOrder().compareTo(p2.getDateOrder());
        }
    };

    public static Collection<Order> getSortedOrders(Collection<Order> orders) {
        Ordering<Order> orderOrdering = Ordering.from(byDate);
        return orderOrdering.reverse().sortedCopy(orders);
    }

    public static Collection<Order> getAllSortedOrders() {
        Collection<Order> ordersSorted = orderDAO.loadAll();
        if (ordersSorted != null) {
            getSortedOrders(ordersSorted);
        }
        return ordersSorted;
    }

    private static Order findOrderByDate(final Date date){
        Predicate<Order> predicate = new Predicate<Order>() {
            public boolean apply(@Nullable Order  order) {
                return order != null &&
                        (order.getDateOrder().equals(date));
            }
        };
        Collection<Order> result =  Collections2.filter(orderDAO.loadAll(), predicate);

        if (result.size() > 1) {
            throw new WorkflowException();
        }

        if (result.isEmpty()) {
            return null;
        }

        return result.iterator().next();
    }

    private static boolean deleteOrder(Order order){
        return orderDAO.delete(order);
    }
}

