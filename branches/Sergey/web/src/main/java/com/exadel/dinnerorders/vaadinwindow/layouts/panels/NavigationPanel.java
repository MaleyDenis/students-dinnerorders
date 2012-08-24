package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.listeners.menucommands.*;
import com.vaadin.ui.*;

public class NavigationPanel extends Panel {
    private MenuBar menuBar;
    private GridLayout layout;
    private final Application application;

    public NavigationPanel(Application application) {
        super();
        this.application = application;
        initLayout();
        initMenuBar();
        addMenuBar();
        setWidth(90, UNITS_PERCENTAGE);
    }

    private void addMenuBar() {
        layout.addComponent(menuBar);
        layout.setComponentAlignment(menuBar, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    private void initLayout() {
        layout = new GridLayout(1, 1);
        layout.setSizeFull();
        layout.setStyleName("menubarlayout");
    }

    private void initMenuBar() {
        menuBar = new MenuBar();
        menuBar.setSizeFull();
        menuBar.setAutoOpen(true);

        MenuBar.MenuItem menuList = menuBar.addItem("Menu", null);
        MenuBar.MenuItem orderList = menuBar.addItem("Order", null);
        MenuBar.MenuItem adminList = menuBar.addItem("Admin tools", null);
        MenuBar.MenuItem chatList = menuBar.addItem("Show chat", new ShowChatCommand(application));
        User user = application.getUser();
        adminList.setVisible(user.getRole() == Role.ADMIN);

        menuList.addItem("Current week", new ShowCurrentWeekMenuCommand(application));
        menuList.addItem("Next week", new ShowNextWeekMenuCommand(application));
        MenuBar.MenuItem allOrders = orderList.addItem("Show all orders", new ShowAllOrdersCommand(application) );
        allOrders.setVisible(user.getRole() == Role.ADMIN);

        MenuBar.MenuItem userOrders = orderList.addItem("Show my orders", new ShowUserOrdersCommand(application) );

        adminList.addItem("Create menu...", new CreateMenuCommand(application));
        adminList.addItem("Manage tasks", new ManageTasksCommand(application));
        menuBar.setStyleName("navigation");
    }
}
