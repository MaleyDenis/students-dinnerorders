package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.TextField;

public class CostChangedListener implements FieldEvents.TextChangeListener {
    @Override
    public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
        String currentValue = (String)((TextField)textChangeEvent.getComponent()).getValue();
        StringBuilder newValue = new StringBuilder(textChangeEvent.getText());
        try {
            Integer.parseInt(newValue.toString());
            if (newValue.lastIndexOf("0") != newValue.length() - 1) {
                newValue.append("0");
            }
            ((TextField)textChangeEvent.getComponent()).setValue(newValue.toString());
            ((TextField)textChangeEvent.getComponent()).setCursorPosition(newValue.lastIndexOf("0"));
        } catch (NumberFormatException nfException) {
            ((TextField)textChangeEvent.getComponent()).setValue(currentValue);
        }
    }
}
