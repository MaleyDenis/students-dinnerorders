package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowNextWeekMenuEvent;
import com.vaadin.ui.MenuBar;


public class ShowNextWeekMenuCommand implements MenuBar.Command {
    private final Application application;

    public ShowNextWeekMenuCommand(Application application) {
        this.application = application;
    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        application.getEventBus().post(new ShowNextWeekMenuEvent());
    }
}
