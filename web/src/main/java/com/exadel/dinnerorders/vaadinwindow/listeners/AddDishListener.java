package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.vaadin.event.MouseEvents;

public class AddDishListener implements MouseEvents.ClickListener {
    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {
        DishDescriptionRow parent = ((DishDescriptionRow)clickEvent.getComponent().getParent());
        AddDishEvent addDishEvent = new AddDishEvent(parent);
        addDishEvent.setAddedDish(new DishDescriptionRow());
        Application.getInstance().getEventBus().post(addDishEvent);
    }
}
