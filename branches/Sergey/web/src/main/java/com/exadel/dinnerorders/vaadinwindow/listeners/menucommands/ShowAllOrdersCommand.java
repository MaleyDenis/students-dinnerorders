package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowAllOrdersEvent;
import com.vaadin.ui.MenuBar;

public class ShowAllOrdersCommand implements MenuBar.Command {
    private final Application application;

    public ShowAllOrdersCommand(Application application) {
        this.application = application;
    }

    public void menuSelected(MenuBar.MenuItem menuItem) {
        application.getEventBus().post(new ShowAllOrdersEvent());
    }
}
