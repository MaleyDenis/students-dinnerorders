package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowCurrentWeekMenuEvent;
import com.vaadin.ui.Button;

public class CancelTaskCreationListener implements Button.ClickListener {
    private final Application application;

    public CancelTaskCreationListener(Application application) {
        this.application = application;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        application.getEventBus().post(new ShowCurrentWeekMenuEvent());
    }
}