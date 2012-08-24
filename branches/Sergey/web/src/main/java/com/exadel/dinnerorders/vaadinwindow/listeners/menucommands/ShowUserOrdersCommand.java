package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowUserOrdersEvent;
import com.vaadin.ui.MenuBar;

public class ShowUserOrdersCommand implements MenuBar.Command {
    private final Application application;

    public ShowUserOrdersCommand(Application application) {
        this.application = application;
    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        application.getEventBus().post(new ShowUserOrdersEvent());
    }
}
