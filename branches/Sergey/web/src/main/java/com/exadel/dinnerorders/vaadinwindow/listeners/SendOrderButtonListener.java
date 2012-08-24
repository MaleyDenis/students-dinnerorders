package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowMadeOrderEvent;
import com.vaadin.ui.Button;


public class SendOrderButtonListener implements Button.ClickListener {
    private final Application application;

    public SendOrderButtonListener(Application application) {
        this.application = application;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        application.getEventBus().post(new ShowMadeOrderEvent());
    }

}
