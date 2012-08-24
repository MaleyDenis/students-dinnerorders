package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Month;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.TasksFactory;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Button;

public class CreateTaskButtonListener implements Button.ClickListener {
    private final Application application;
    public CreateTaskButtonListener(Application application) {
        this.application = application;
    }

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
        String month = tcPanel.getMonthSelect().getValue().toString().equals("*") ? "*" :
                Integer.toString(Month.valueOf(tcPanel.getMonthSelect().getValue().toString()).ordinal() + 1);
        taskBuilder.append(month);
        String weekday = tcPanel.getWeekdaySelect().getValue().toString().equals("*") ? "*" :
                Integer.toString(Weekday.valueOf(tcPanel.getWeekdaySelect().getValue().toString()).ordinal() + 1);
        taskBuilder.append(' ');
        taskBuilder.append(weekday);
        taskBuilder.append(' ');
        taskBuilder.append(chooseClass(tcPanel.getActionSelect().getValue()));

        application.getTasksManagerService().addTask(TasksFactory.createTask(taskBuilder.toString()));

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
        } else if (targetTask.equals("Database auto-reimport")) {
            return "com.exadel.dinnerorders.entity.tasks.ReimportDatabaseTask";
        }
        return null;
    }
}