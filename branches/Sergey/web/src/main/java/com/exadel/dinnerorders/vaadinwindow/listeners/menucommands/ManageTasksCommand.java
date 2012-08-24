package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowTasksCreationPanelEvent;
import com.vaadin.ui.MenuBar;

public class ManageTasksCommand implements MenuBar.Command {
    private final Application application;

    public ManageTasksCommand(Application application) {
        this.application = application;
    }

    @Override
    public void menuSelected(MenuBar.MenuItem selectedItem) {
        application.getEventBus().post(new ShowTasksCreationPanelEvent());
    }
}