package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowNextWeekMenuEvent;
import com.vaadin.ui.MenuBar;


public class ShowNextWeekMenuCommand implements MenuBar.Command {
    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        Application.getInstance().getEventBus().post(new ShowNextWeekMenuEvent());
    }
}
