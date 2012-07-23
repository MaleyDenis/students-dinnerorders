package com.exadel.dinnerorders.vaadinwindow.listener;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.RemoveDishEvent;
import com.vaadin.ui.Button;

public class RemoveDishListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Application.getInstance().getEventBus().post(new RemoveDishEvent(clickEvent.getButton().getParent()));
    }
}