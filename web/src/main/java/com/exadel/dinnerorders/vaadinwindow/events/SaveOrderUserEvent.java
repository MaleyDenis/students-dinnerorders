package com.exadel.dinnerorders.vaadinwindow.events;

import com.vaadin.ui.GridLayout;

public class SaveOrderUserEvent {

    private final GridLayout parent;

    public SaveOrderUserEvent(GridLayout parent) {
        this.parent = parent;
    }

    public GridLayout getParent() {
        return parent;
    }
}
