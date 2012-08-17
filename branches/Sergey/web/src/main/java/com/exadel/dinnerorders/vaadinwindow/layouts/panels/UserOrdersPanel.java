package com.exadel.dinnerorders.vaadinwindow.layouts.panels;


import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.OrderService;
import com.exadel.dinnerorders.service.UserService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.guitoolkits.OrderMenuLayout;
import com.vaadin.ui.*;

import java.util.Collection;
import java.util.Date;

public class UserOrdersPanel extends Panel {
    public static final int DEFAULT_COLUMNS = 1;
    public static final int DEFAULT_ROWS = 3;
    private GridLayout gridLayout;
    private Table orderUserTable;
    private PopupView popupView;

    public UserOrdersPanel(){
        super();
        initComponent();
        locateComponent();
    }

    public void initComponent(){
        initGridLayout();
        initTable();
    }

    private void initGridLayout(){
        gridLayout = new GridLayout(DEFAULT_COLUMNS,DEFAULT_ROWS);
        gridLayout.setWidth(100, UNITS_PERCENTAGE);
        gridLayout.setHeight(100, UNITS_PERCENTAGE);
    }

    private void initTable(){
        orderUserTable = new Table();
        orderUserTable.addContainerProperty("Date order", Date.class,null);
        orderUserTable.addContainerProperty("Data payment",Date.class,null);
        orderUserTable.addContainerProperty("Description", PopupView.class,"Show menu");
        orderUserTable.setSortDisabled(true);
        orderUserTable.setSizeFull();

    }


    private void createTable(){
        Collection<Order> orderUser = OrderService.getAllSortedOrders();
        User user = UserService.findUserByUserName(Application.getInstance().getUser().getUserName());
        int i = 1;
        Long userID = user.getId();
        for(Order order: orderUser){
            if (order.getUserID().equals(userID)){
               orderUserTable.addItem(new Object[]{order.getDateOrder(),order.getDatePayment(),viewOrderMenu(order)},i);
               i++;
            }
        }

    }

    public PopupView viewOrderMenu(Order order){
        Panel panel = new Panel();
        panel.setWidth(50,UNITS_PERCENTAGE);
        panel.setHeight(50,UNITS_PERCENTAGE);
        panel.addComponent(new OrderMenuLayout(order));
        popupView = new PopupView("Description",panel);
        return popupView;
    }

    private void locateComponent(){
        createTable();
        gridLayout.addComponent(new Label(),0,0);
        gridLayout.addComponent(orderUserTable,0,1);
        addComponent(gridLayout);
    }

}
