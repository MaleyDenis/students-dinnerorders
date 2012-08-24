package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveOrderUserEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;

public class SaveOrderButtonListener implements Button.ClickListener{
    private final Application application;

    public SaveOrderButtonListener(Application application) {
        this.application = application;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        GridLayout mainPanel = (GridLayout)clickEvent.getButton().getParent();
        application.getEventBus().post(new SaveOrderUserEvent(mainPanel));
    }
}
