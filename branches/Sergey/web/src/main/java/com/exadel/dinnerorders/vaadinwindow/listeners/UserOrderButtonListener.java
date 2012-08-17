package com.exadel.dinnerorders.vaadinwindow.listeners;


import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveMenuUserEvent;
import com.exadel.dinnerorders.vaadinwindow.events.ShowOrderUserEvent;
import com.vaadin.ui.Button;

public class UserOrderButtonListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        Application.getInstance().getEventBus().post(new ShowOrderUserEvent(clickEvent.getButton().getParent()));
        Application.getInstance().getEventBus().post(new SaveMenuUserEvent());

    }
}
