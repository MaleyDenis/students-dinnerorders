package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowMenuCreationPanelEvent;
import com.vaadin.ui.MenuBar;

public class CreateMenuCommand implements MenuBar.Command {
    private final Application application;

    public CreateMenuCommand(Application application) {
        this.application = application;
    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        application.getEventBus().post(new ShowMenuCreationPanelEvent());
    }
}
