package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.OrderService;
import com.exadel.dinnerorders.service.UserService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TableOrderPanel extends Panel {
    private Table tableOrder;
    private Label user;
    private Label cost;
    private Label dateOrder;
    private Label datePayment;
    private Label menu;
    private VerticalLayout verticalLayout;
    private PopupView popupView;
    private Table menuItemOrderTable;
    private Label dish;
    private Label costMenuItem;
    public TableOrderPanel(){
        super();
        Application.getInstance().getEventBus().register(this);
        initLabels();
        initTableOrder();
        setSizeFull();

    }

    public void initLabels(){
        user = new Label("User");
        cost = new Label("Cost");
        dateOrder = new Label("Date order");
        datePayment = new Label("Date payment");
        menu = new Label("Menu");
        dish = new Label("Dish");
        costMenuItem = new Label("Cost");


    }
    public void initMenuItemOrderTable(){
        menuItemOrderTable = new Table("Menu");
        menuItemOrderTable.addContainerProperty(dish,String.class,null);
        menuItemOrderTable.addContainerProperty(costMenuItem,String.class,null);

    }

    public void initTableOrder(){
        tableOrder = new Table("Order");
        tableOrder.addContainerProperty(user,String.class,null);
        tableOrder.addContainerProperty(cost,Double.class,null);
        tableOrder.addContainerProperty(dateOrder, Date.class,null);
        tableOrder.addContainerProperty(datePayment,Date.class,null);
        tableOrder.addContainerProperty(menu,PopupView.class,null);
        tableOrder.setSizeFull();
        createTable();
        addComponent(tableOrder);
    }

    public void createTable(){
        Collection<Order> orderCollection = OrderService.sortedAll();
        ArrayList<String> menuItemOrder;
        int i=1;
        int k=1;
        for (Order order : orderCollection){
            initMenuItemOrderTable();
            menuItemOrder = new ArrayList<String>();
            for(MenuItem menuItem:order.getMenuItemList()) {
                menuItemOrder.add(menuItem.getWeekday() +"\n"+ menuItem.getDescription()+" - price: "+menuItem.getCost());
                menuItemOrderTable.addItem(new Object[]{menuItem.getWeekday(),menuItem.getDescription()},k);
                k++;
            }
            tableOrder.addItem(new Object[]{UserService.findUserByID(order.getUserID()).getUserName(),order.getCost(),order.getDateOrder(),
                    order.getDatePayment(),initPopupView(menuItemOrderTable)},i);
            i++;
        }
    }

    public void initVerticalLayout(){
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        verticalLayout = new VerticalLayout();
        verticalLayout.setWidth(width * 0.5f, UNITS_PIXELS);
        verticalLayout.setHeight(height*0.3f,UNITS_PIXELS);

    }

    public PopupView initPopupView(Table menuItemOrderTable){
        initVerticalLayout();
        menuItemOrderTable.setSizeFull();
        verticalLayout.addComponent(menuItemOrderTable);
        popupView = new PopupView("Show details",verticalLayout);
        popupView.setHideOnMouseOut(false);
        return popupView;
    }

}
