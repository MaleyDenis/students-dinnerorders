package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowCurrentWeekMenuEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.ui.Button;

public class CancelButtonListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        MenuCreationPanel panel = (MenuCreationPanel)clickEvent.getButton().getParent().getParent();
        panel.flush();
        Application.getInstance().getEventBus().post(new ShowCurrentWeekMenuEvent());
    }
}
