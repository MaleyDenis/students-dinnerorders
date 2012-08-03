package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowAllOrdersEvent;
import com.vaadin.ui.MenuBar;

public class ShowAllOrdersCommand implements MenuBar.Command {
    public void menuSelected(MenuBar.MenuItem menuItem) {
        Application.getInstance().getEventBus().post(new ShowAllOrdersEvent());
    }
}
