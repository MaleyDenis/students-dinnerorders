package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveOrderUserEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;

public class SaveOrderButtonListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        GridLayout mainPanel = (GridLayout)clickEvent.getButton().getParent();
        Application.getInstance().getEventBus().post(new SaveOrderUserEvent(mainPanel));
    }
}
