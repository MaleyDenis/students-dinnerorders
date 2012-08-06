package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Month;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.NativeSelect;

public class MonthSelectListener implements Component.Listener {
    @Override
    public void componentEvent(Component.Event event) {
        TasksCreationPanel panel;
        try{
            panel = (TasksCreationPanel)event.getComponent().getParent().getParent();
            if (panel == null) {
                return;
            }
        } catch (Exception exception) {
            return;
        }
        String value = (String)((NativeSelect)event.getSource()).getValue();
        if (value.equals("*")) {
            value = Month.FEBRUARY.name();
        }
        NativeSelect daySelect = panel.getDaySelect();
        String currentDay = daySelect.getValue().toString();
        daySelect.removeAllItems();
        daySelect.setNullSelectionAllowed(false);
        int daysInMonth = Month.getDaysInMonth(Month.valueOf(value));
        String newValue = compareValues(currentDay, daysInMonth);
        daySelect.addItem("*");
        for (int i = 1; i <= daysInMonth; i++) {
            daySelect.addItem(Integer.toString(i));
        }
        daySelect.setValue(newValue);
    }

    private String compareValues(String currentDay, int daysInMonth) {
        if (currentDay.equals("*")) {
            return currentDay;
        } else {
            int currDate = Integer.parseInt(currentDay);
            return currDate > daysInMonth ? Integer.toString(daysInMonth) : currentDay;
        }
    }
}
