package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.NativeSelect;

public class DateBoxListener implements Component.Listener {
    @Override
    public void componentEvent(Component.Event event) {
        MenuCreationPanel panel = (MenuCreationPanel)event.getComponent().getParent().getParent();
        String value = (String)((NativeSelect)event.getSource()).getValue();
        if (value == null) {
            panel.setDate(null, null);
        } else if (value.contains("Current week")) {
            panel.setDate(DateUtils.getCurrentMondayTime(), DateUtils.getCurrentFridayTime());
        } else if (value.contains("Next week")) {
            panel.setDate(DateUtils.getNextMondayTime(), DateUtils.getNextFridayTime());
        }
    }
}
