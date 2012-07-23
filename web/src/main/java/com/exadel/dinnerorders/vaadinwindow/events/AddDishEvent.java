package com.exadel.dinnerorders.vaadinwindow.events;

import com.vaadin.ui.Component;

public class AddDishEvent {
    private final Component parent;

    public AddDishEvent(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }
}
