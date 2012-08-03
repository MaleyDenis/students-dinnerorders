package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowMadeOrderEvent;
import com.vaadin.ui.Button;


public class SendOrderButtonListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Application.getInstance().getEventBus().post(new ShowMadeOrderEvent());
    }

}
