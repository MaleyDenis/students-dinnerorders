package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Button;

public class RemoveTaskListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent event) {
        TasksCreationPanel panel = (TasksCreationPanel)event.getButton().getParent().getParent();
        String listValue = panel.getTasksList().getValue().toString();
        int taskIndex = getTaskIndex(listValue);
        panel.getTasksList().removeItem(listValue);
        Application.getInstance().getTasksManagerService().getTasksList().remove(taskIndex);
        panel.hideButtons();
        panel.hideSelections();
        panel.updateList();
    }

    private int getTaskIndex(String taskName) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; taskName.charAt(i) != '.'; i++) {
            builder.append(taskName.charAt(i));
        }
        return Integer.parseInt(builder.toString()) - 1;
    }
}
