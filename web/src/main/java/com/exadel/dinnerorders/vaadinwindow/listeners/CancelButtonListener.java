package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowCurrentWeekMenuEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.ui.Button;

public class CancelButtonListener implements Button.ClickListener {
    private final Application application;

    public CancelButtonListener(Application application) {
        this.application = application;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        MenuCreationPanel panel = (MenuCreationPanel)clickEvent.getButton().getParent().getParent();
        panel.flush();
        application.getEventBus().post(new ShowCurrentWeekMenuEvent());
    }
}
