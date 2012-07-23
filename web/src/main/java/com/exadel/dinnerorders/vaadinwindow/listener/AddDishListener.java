package com.exadel.dinnerorders.vaadinwindow.listener;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.vaadin.ui.Button;

public class AddDishListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Application.getInstance().getEventBus().post(new AddDishEvent(clickEvent.getButton().getParent()));
    }
}
