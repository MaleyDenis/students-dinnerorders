package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Month;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.TasksFactory;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Button;

public class CreateTaskButtonListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        StringBuilder taskBuilder = new StringBuilder();
        TasksCreationPanel tcPanel = (TasksCreationPanel)clickEvent.getButton().getParent().getParent();
        taskBuilder.append(tcPanel.getMinutesSelect().getValue());
        taskBuilder.append(' ');
        taskBuilder.append(tcPanel.getHoursSelect().getValue());
        taskBuilder.append(' ');
        taskBuilder.append(tcPanel.getDaySelect().getValue());
        taskBuilder.append(' ');
        taskBuilder.append(Month.valueOf(tcPanel.getMonthSelect().getValue().toString()).ordinal() + 1);
        taskBuilder.append(' ');
        taskBuilder.append(Weekday.valueOf(tcPanel.getWeekdaySelect().getValue().toString()).ordinal() + 1);
        taskBuilder.append(' ');
        taskBuilder.append(chooseClass(tcPanel.getActionSelect().getValue()));
        Application.getInstance().getTasksManagerService().addTask(TasksFactory.createTask(taskBuilder.toString()));
        tcPanel.hideButtons();
        tcPanel.hideSelections();
        tcPanel.updateList();
    }

    private String chooseClass(Object value) {
        String targetTask = value.toString();
        if (targetTask.equals("Delete old menus")) {
            return "com.exadel.dinnerorders.entity.tasks.ClearMenuTableTask";
        } else if (targetTask.equals("Delete old orders")) {
            return "com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask";
        }
        return null;
    }
}