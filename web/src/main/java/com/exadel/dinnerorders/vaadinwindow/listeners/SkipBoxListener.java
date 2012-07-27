package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.DayMenuPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.ui.Button;

public class SkipBoxListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        boolean value = !clickEvent.getButton().booleanValue();
        setComponentsStatus(clickEvent, value);
    }

    private void setComponentsStatus(Button.ClickEvent clickEvent, boolean value) {
        DishDescriptionRow row = (DishDescriptionRow)clickEvent.getButton().getParent();
        row.getDishName().setEnabled(value);
        row.getCost().setEnabled(value);
        row.getAdd().setEnabled(value);
        row.getRemove().setEnabled(value);
        DayMenuPanel dayPanel = (DayMenuPanel)row.getParent();
        dayPanel.setModifyingEnabled();
        checkItemsCount(row, value);
    }

    private void checkItemsCount(DishDescriptionRow row, boolean value) {
        DayMenuPanel dayPanel = (DayMenuPanel)row.getParent();
        if (value == false) {
            ((MenuCreationPanel)dayPanel.getParent().getParent()).decrementMenuItemsCount();
        } else if (value == true) {
            ((MenuCreationPanel)dayPanel.getParent().getParent()).incrementMenuItemsCount();
        }
        if (((MenuCreationPanel)dayPanel.getParent().getParent()).getMenuItemsCounter() == 0) {
            ((MenuCreationPanel)dayPanel.getParent().getParent()).getSaveButton().setEnabled(false);
        } else {
            ((MenuCreationPanel)dayPanel.getParent().getParent()).getSaveButton().setEnabled(true);
        }
    }
}
