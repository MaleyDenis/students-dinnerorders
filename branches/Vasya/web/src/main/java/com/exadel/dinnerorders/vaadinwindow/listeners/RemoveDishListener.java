package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.RemoveDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.vaadin.event.MouseEvents;

public class RemoveDishListener implements MouseEvents.ClickListener {
    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {
        DishDescriptionRow parent = ((DishDescriptionRow)clickEvent.getComponent().getParent());
        Application.getInstance().getEventBus().post(new RemoveDishEvent(parent));
    }
}