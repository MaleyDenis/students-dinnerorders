package com.exadel.dinnerorders.vaadinwindow.listeners;


import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveMenuUserEvent;
import com.exadel.dinnerorders.vaadinwindow.events.ShowOrderUserEvent;
import com.vaadin.ui.Button;

public class UserOrderButtonListener implements Button.ClickListener{
    private final Application application;

    public UserOrderButtonListener(Application application) {
        this.application = application;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        application.getEventBus().post(new ShowOrderUserEvent(clickEvent.getButton().getParent()));
        application.getInstance().getEventBus().post(new SaveMenuUserEvent());

    }
}
