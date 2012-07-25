package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.vaadinwindow.listeners.menucommands.CreateMenuCommand;
import com.exadel.dinnerorders.vaadinwindow.listeners.menucommands.ShowCurrentWeekMenuCommand;
import com.exadel.dinnerorders.vaadinwindow.listeners.menucommands.ShowMyOrdersCommand;
import com.exadel.dinnerorders.vaadinwindow.listeners.menucommands.ShowNextWeekMenuCommand;
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

        menuList.addItem("Current week", new ShowCurrentWeekMenuCommand());
        menuList.addItem("Next week", new ShowNextWeekMenuCommand());
        orderList.addItem("My orders...", new ShowMyOrdersCommand() );
        adminList.addItem("Create menu...", new CreateMenuCommand());
    }
}
