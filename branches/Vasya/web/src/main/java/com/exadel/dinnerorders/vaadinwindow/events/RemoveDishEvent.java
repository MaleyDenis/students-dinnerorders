package com.exadel.dinnerorders.vaadinwindow.events;


import com.vaadin.ui.Component;

public class RemoveDishEvent {
    private final Component parent;

    public RemoveDishEvent(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }
}
