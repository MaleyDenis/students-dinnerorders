package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.OrderService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveOrderUserEvent;
import com.exadel.dinnerorders.vaadinwindow.guitoolkits.OrderMenuLayout;
import com.exadel.dinnerorders.vaadinwindow.listeners.SaveOrderButtonListener;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;


public class ShowMadeOrderPanel extends Panel {

    private static final int DEFAULT_COLUMNS = 1;
    private static final int DEFAULT_ROWS = 3;
    private Label orderLabel;
    private GridLayout gridLayout;
    private Button saveOrderButton;
    private Order order;
    private final Application application;

    public ShowMadeOrderPanel(Application application){
        super();
        this.application = application;
        order = this.application.getOrder();
        initComponent();
        locateComponents();
        application.getEventBus().register(this);

    }

    private void  initLayout(){
        gridLayout = new GridLayout(DEFAULT_COLUMNS,DEFAULT_ROWS);
        gridLayout.setWidth(100, UNITS_PERCENTAGE);
        gridLayout.setHeight(100, UNITS_PERCENTAGE);
        addComponent(gridLayout);
    }

    private void createMenuUser(){
        //panel.addComponent(new OrderMenuLayout(order));
        gridLayout.addComponent(new OrderMenuLayout(order),0,1);
    }

    public void initComponent(){
        initLayout();
        orderLabel = new Label("Your order");
        saveOrderButton = new Button("Send Order");
        saveOrderButton.addListener(new SaveOrderButtonListener(application));

    }

    private void locateComponents() {
        gridLayout.addComponent(orderLabel, 0,0);
        gridLayout.addComponent(saveOrderButton, 0, 2);
        createMenuUser();
        gridLayout.setComponentAlignment(orderLabel, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(saveOrderButton, Alignment.MIDDLE_CENTER);
    }

    @Subscribe
    public void saveOrder(SaveOrderUserEvent saveOrderUserEvent){
        GridLayout eventParent = saveOrderUserEvent.getParent();
        if (eventParent != gridLayout) {
            return;
        }
        getWindow().showNotification("Your order is accept");
        OrderService.saveOrder(order);
    }




}
