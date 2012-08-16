package com.exadel.dinnerorders.vaadinwindow.guitoolkits;

import com.exadel.dinnerorders.entity.Order;

public class UIOrderUtils {

    public static void viewOrderMenu(Order order){
        new OrderMenuLayout(order);
    }

}
