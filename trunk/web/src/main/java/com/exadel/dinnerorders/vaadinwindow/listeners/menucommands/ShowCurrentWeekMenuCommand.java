package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowCurrentWeekMenuEvent;
import com.vaadin.ui.MenuBar;

public class ShowCurrentWeekMenuCommand implements MenuBar.Command {
    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        Application.getInstance().getEventBus().post(new ShowCurrentWeekMenuEvent());
    }
}
