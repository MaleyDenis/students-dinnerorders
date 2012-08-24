package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.vaadin.event.MouseEvents;

public class AddDishListener implements MouseEvents.ClickListener {
    private final Application application;

    public AddDishListener(Application application) {
        this.application = application;
    }

    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {
        DishDescriptionRow parent = ((DishDescriptionRow)clickEvent.getComponent().getParent());
        AddDishEvent addDishEvent = new AddDishEvent(parent);
        addDishEvent.setAddedDish(new DishDescriptionRow(application));
        application.getEventBus().post(addDishEvent);
    }
}
