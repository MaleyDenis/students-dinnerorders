package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveMenuEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.DayMenuPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

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

        int startFromRow = 1;
        for (int i = 0; i < MenuCreationPanel.NUMBER_OF_SERVICE_DAYS; i++) {
            DayMenuPanel dayPanel = (DayMenuPanel)mainPanel.getComponent(0, startFromRow);
            expected++;
            actual += dayPanel.isDataValid() ? 1 : 0;
            startFromRow += 2;
        }

        if (actual == expected) {
            Application.getInstance().getEventBus().post(new SaveMenuEvent(mainPanel));
        }
    }
}