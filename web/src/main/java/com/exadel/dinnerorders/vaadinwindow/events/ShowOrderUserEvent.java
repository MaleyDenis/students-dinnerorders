package com.exadel.dinnerorders.vaadinwindow.events;


import com.vaadin.ui.Component;

public class ShowOrderUserEvent {

    private Component parent;

    public ShowOrderUserEvent(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }
}
