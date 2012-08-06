package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowTasksCreationPanelEvent;
import com.vaadin.ui.MenuBar;

public class ManageTasksCommand implements MenuBar.Command {
    @Override
    public void menuSelected(MenuBar.MenuItem selectedItem) {
        Application.getInstance().getEventBus().post(new ShowTasksCreationPanelEvent());
    }
}
