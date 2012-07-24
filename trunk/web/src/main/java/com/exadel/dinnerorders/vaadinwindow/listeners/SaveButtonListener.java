package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveMenuEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.DayMenuPanel;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

import java.util.Iterator;

public class SaveButtonListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        GridLayout mainPanel = (GridLayout)clickEvent.getButton().getParent();
        int expected = 1;
        int actual = 0;
        if (((TextField)mainPanel.getComponent(0, 0)).getValue().equals("")) {
            mainPanel.getComponent(0, 0).setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/error.png"));
        } else {
            mainPanel.getComponent(0, 0).setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/ok.png"));
            actual++;
        }
        Iterator<Component> iterator = mainPanel.getComponentIterator();

        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof DayMenuPanel) {
                expected++;
                actual += ((DayMenuPanel)object).isDataValid() ? 1 : 0;
            }
        }

        if (actual == expected) {
            Application.getInstance().getEventBus().post(new SaveMenuEvent(mainPanel));
        }
    }
}