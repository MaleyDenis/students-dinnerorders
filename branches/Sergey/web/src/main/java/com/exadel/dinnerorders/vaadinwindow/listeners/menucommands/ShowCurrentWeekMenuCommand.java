package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowCurrentWeekMenuEvent;
import com.vaadin.ui.MenuBar;

public class ShowCurrentWeekMenuCommand implements MenuBar.Command {
    private final Application application;

    public ShowCurrentWeekMenuCommand(Application application) {
        this.application = application;
    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        application.getEventBus().post(new ShowCurrentWeekMenuEvent());
    }
}
