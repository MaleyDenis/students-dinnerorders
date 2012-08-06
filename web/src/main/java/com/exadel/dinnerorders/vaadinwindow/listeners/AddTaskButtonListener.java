package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Button;

public class AddTaskButtonListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent event) {
        TasksCreationPanel panel = (TasksCreationPanel)event.getButton().getParent().getParent();
        panel.showButtons();
        panel.getEdit().setEnabled(false);
        panel.getRemove().setEnabled(false);
        panel.showSelections();
    }
}
