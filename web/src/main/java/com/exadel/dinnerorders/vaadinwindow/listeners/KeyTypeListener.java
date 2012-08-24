package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class KeyTypeListener implements FieldEvents.TextChangeListener {
    @Override
    public void textChange(FieldEvents.TextChangeEvent event) {
        if (event.getText().contains("\n")) {
            Button button = (Button)((VerticalLayout)((GridLayout)event.getComponent().getParent())
                    .getComponent(1, 0)).getComponent(0);
            ((TextArea)event.getComponent()).setValue(event.getText());
            button.click();
        }
    }
}
