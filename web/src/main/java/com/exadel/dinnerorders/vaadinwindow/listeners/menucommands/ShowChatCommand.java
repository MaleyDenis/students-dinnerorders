package com.exadel.dinnerorders.vaadinwindow.listeners.menucommands;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowChatBoardEvent;
import com.vaadin.ui.MenuBar;

public class ShowChatCommand implements MenuBar.Command {
    @Override
    public void menuSelected(MenuBar.MenuItem selectedItem) {
        Application.getInstance().getEventBus().post(new ShowChatBoardEvent());
    }
}
