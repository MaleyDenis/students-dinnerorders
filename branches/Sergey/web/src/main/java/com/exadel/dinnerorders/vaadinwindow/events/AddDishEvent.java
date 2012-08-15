package com.exadel.dinnerorders.vaadinwindow.events;

import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.vaadin.ui.Component;

public class AddDishEvent {
    private final Component parent;
    private DishDescriptionRow addedDish;

    public DishDescriptionRow getAddedDish() {
        return addedDish;
    }

    public void setAddedDish(DishDescriptionRow addedDish) {
        this.addedDish = addedDish;
    }

    public AddDishEvent(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }
}
