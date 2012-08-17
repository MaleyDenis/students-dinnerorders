package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.dao.OrderDAO;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.exception.WorkflowException;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class OrderService {

    private static OrderDAO orderDAO = new OrderDAO();
    private int id;

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

    public static boolean saveOrder(Order order) {
        orderDAO.create(order);
        return true;
    }

    public static Order findOrderByDate(final Date date){
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

    public static Double getCostOrder(List<MenuItem> menuItems) {
        Double costOrder = 0d;
        for (MenuItem menuItem : menuItems) {
            costOrder+=menuItem.getCost();
        }
        return costOrder;
    }

    public int hashCode() {
        return ((id>>>32) ^ id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderService that = (OrderService) o;

        return id == that.id;
    }

    public static boolean deleteOrder(Order order){
        List<MenuItem> orderItems = order.getMenuItemList();
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        for (MenuItem menuItem : orderItems){
            menuItemDAO.delete(menuItem);
        }
        return orderDAO.delete(order);
    }

    public static Collection<Order> findOrderBeforeDate(final Date date) {
        Predicate<Order> predicate = new Predicate<Order>() {
            public boolean apply(@Nullable Order  order) {
                return order != null &&
                        (order.getDateOrder().equals(date) || order.getDateOrder().before(date));
            }
        };
        return Collections2.filter(orderDAO.loadAll(), predicate);
    }

    public static boolean deleteAll(Collection<Order> orders) {
        boolean result = true;
        for (Order order : orders) {
            result = result && deleteOrder(order);
        }
        return result;
    }
}

