package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Month;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.entity.tasks.Task;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Button;

public class EditTaskButtonListener implements Button.ClickListener {
    private final Application application;

    public EditTaskButtonListener(Application application) {
        this.application = application;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        TasksCreationPanel panel = (TasksCreationPanel)event.getButton().getParent().getParent();
        int minutes = panel.getMinutesSelect().getValue().equals("*") ? -1 :
                Integer.parseInt(panel.getMinutesSelect().getValue().toString());
        int hours = panel.getHoursSelect().getValue().equals("*") ? -1 :
                Integer.parseInt(panel.getHoursSelect().getValue().toString());
        int day = panel.getDaySelect().getValue().equals("*") ? -1 :
                Integer.parseInt(panel.getDaySelect().getValue().toString());
        int month = panel.getMonthSelect().getValue().equals("*") ? -1 :
                Month.valueOf(panel.getMonthSelect().getValue().toString()).ordinal() + 1;
        int weekday = panel.getWeekdaySelect().getValue().equals("*") ? -1 :
                Weekday.valueOf(panel.getWeekdaySelect().getValue().toString()).ordinal() + 1;
        Task task = getSelectedTask(panel);
        task.setMinutes(minutes);
        task.setHours(hours);
        task.setDayOfMonth(day);
        task.setMonth(month);
        task.setDayOfWeek(weekday);
        panel.hideButtons();
        panel.hideSelections();
        panel.updateList();
    }

    private Task getSelectedTask(TasksCreationPanel panel) {
        String taskName = panel.getTasksList().getValue().toString();
        int index = getTaskIndex(taskName);
        return application.getTasksManagerService().getTasksList().get(index);
    }

    private int getTaskIndex(String taskName) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; taskName.charAt(i) != '.'; i++) {
            builder.append(taskName.charAt(i));
        }
        return Integer.parseInt(builder.toString()) - 1;
    }
}