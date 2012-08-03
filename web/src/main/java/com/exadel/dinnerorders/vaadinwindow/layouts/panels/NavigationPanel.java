package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.listeners.menucommands.*;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;

public class NavigationPanel extends Panel {
    private MenuBar menuBar;
    public NavigationPanel() {
        super();
        initMenuBar();
        addComponent(menuBar);
        setWidth(90, UNITS_PERCENTAGE);
    }

    private void initMenuBar() {
        menuBar = new MenuBar();
        menuBar.setWidth("100%");
        menuBar.setAutoOpen(true);

        MenuBar.MenuItem menuList = menuBar.addItem("Menu", null);
        MenuBar.MenuItem orderList = menuBar.addItem("Order", null);
        MenuBar.MenuItem adminList = menuBar.addItem("Admin tools", null);
        User user = Application.getInstance().getUser();
        adminList.setVisible(user.getRole() == Role.ADMIN);

        menuList.addItem("Current week", new ShowCurrentWeekMenuCommand());
        menuList.addItem("Next week", new ShowNextWeekMenuCommand());
        MenuBar.MenuItem allOrders = orderList.addItem("Show all orders", new ShowAllOrdersCommand() );
        allOrders.setVisible(user.getRole() == Role.ADMIN);


        MenuBar.MenuItem userOrders = orderList.addItem("Show my orders", new ShowUserOrdersCommand() );

        adminList.addItem("Create menu...", new CreateMenuCommand());
        adminList.addItem("Manage tasks", new ManageTasksCommand());
    }
}
