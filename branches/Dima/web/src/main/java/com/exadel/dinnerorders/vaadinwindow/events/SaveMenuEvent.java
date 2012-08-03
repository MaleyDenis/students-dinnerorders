package com.exadel.dinnerorders.vaadinwindow.events;

import com.vaadin.ui.GridLayout;

public class SaveMenuEvent {
    private final GridLayout parent;

    public SaveMenuEvent(GridLayout parent) {
        this.parent = parent;
    }

    public GridLayout getParent() {
        return parent;
    }
}
