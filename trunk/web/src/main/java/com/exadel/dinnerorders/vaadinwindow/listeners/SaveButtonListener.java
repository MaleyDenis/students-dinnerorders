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
    private final int DEFAULT_EXPECTED = 2;
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        GridLayout mainPanel = (GridLayout)clickEvent.getButton().getParent();
        int expected = DEFAULT_EXPECTED;
        int actual = 0;
        if (checkCafeNameField (mainPanel)) {
            actual++;
        }
        if (checkDateBox(mainPanel)) {
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

    private boolean checkDateBox(GridLayout mainPanel) {
        if ( ((MenuCreationPanel)mainPanel.getParent()).getMondayDate() == null) {
            mainPanel.getComponent(1, 0).setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/error.png"));
            return false;
        } else {
            mainPanel.getComponent(1, 0).setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/ok.png"));
            return true;
        }
    }

    private boolean checkCafeNameField(GridLayout mainPanel) {
        if (((TextField)mainPanel.getComponent(0, 0)).getValue().equals("")) {
            mainPanel.getComponent(0, 0).setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/error.png"));
            return false;
        } else {
            mainPanel.getComponent(0, 0).setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/ok.png"));
            return true;
        }
    }
}