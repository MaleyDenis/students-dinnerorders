package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.RemoveDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.vaadin.event.MouseEvents;

public class RemoveDishListener implements MouseEvents.ClickListener {
    private final Application application;

    public RemoveDishListener(Application application) {
        this.application = application;
    }

    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {
        DishDescriptionRow parent = ((DishDescriptionRow)clickEvent.getComponent().getParent());
        application.getEventBus().post(new RemoveDishEvent(parent));
    }
}