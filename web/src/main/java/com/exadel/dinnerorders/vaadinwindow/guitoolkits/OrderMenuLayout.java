package com.exadel.dinnerorders.vaadinwindow.guitoolkits;


import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.Weekday;
import com.vaadin.ui.*;

import java.util.*;

public class OrderMenuLayout extends GridLayout {

    private Order order;
    private Panel orderPanel;
    private Label orderLabel;

    public OrderMenuLayout(Order order){
        super();
        this.order = order;
        initComponent();
        locateComponent();
        setSizeFull();
    }

    private void initComponent(){
        orderLabel = new Label();
        orderPanel = new Panel();
        orderPanel.setSizeFull();


    }

    private TreeSet getWeekdayMenuItem(){
        List<MenuItem> menuItems = order.getMenuItemList();
        TreeSet<Weekday> weekdays = new TreeSet<Weekday>();
        for(int i=1; i<=Weekday.values().length; i++){
            for (MenuItem menuItem: menuItems){
                if(Weekday.getWeekday(i).equals(menuItem.getWeekday()))
                weekdays.add(menuItem.getWeekday());
            }
        }
        return weekdays;
    }

    private void locatePanelMenuItem(Weekday weekday){
        Label weekdayLabel = new Label("<h1>"+weekday.toString()+"</h1>");
        weekdayLabel.setContentMode(Label.CONTENT_XHTML);
        weekdayLabel.setWidth(40,UNITS_PIXELS);
        addComponent(weekdayLabel);
        setComponentAlignment(weekdayLabel,Alignment.MIDDLE_CENTER);
        orderPanel = new Panel();
        orderPanel.setSizeFull();
    }

    private void createMenu(){
        List<MenuItem> menuItems = order.getMenuItemList();
        TreeSet<Weekday> weekdays = getWeekdayMenuItem();
        for (Weekday weekday: weekdays){
            locatePanelMenuItem(weekday);
            for (MenuItem menuItem : menuItems){
                if(weekday.equals(menuItem.getWeekday())){
                    DayMenuLayout dayMenuLayout = new DayMenuLayout(menuItem.getDescription(),menuItem.getCost());
                    orderPanel.addComponent(dayMenuLayout);
                }
            }
            addComponent(orderPanel);
        }

    }

    private void locateComponent(){
        createMenu();
        getWeekdayMenuItem();
        addComponent(orderLabel);
        setComponentAlignment(orderLabel, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(orderPanel, Alignment.MIDDLE_CENTER);

    }


}
