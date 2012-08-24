package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.SendMessageButtonEvent;
import com.vaadin.ui.Button;

public class SendMessageButtonListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        ((WebApplicationController)clickEvent.getButton().getApplication()).getApplication()
                .getEventBus().post(new SendMessageButtonEvent());
    }
}
