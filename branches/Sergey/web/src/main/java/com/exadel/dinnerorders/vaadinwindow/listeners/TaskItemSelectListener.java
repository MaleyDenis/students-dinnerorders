package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Month;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.entity.tasks.Task;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TasksCreationPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;

public class TaskItemSelectListener implements Component.Listener {
    @Override
    public void componentEvent(Component.Event event) {
        if ( ((ListSelect)event.getComponent()).getValue() != null) {
            ListSelect list = ((ListSelect)event.getComponent());
            TasksCreationPanel panel = (TasksCreationPanel)event.getComponent().getParent().getParent();
            panel.getEdit().setEnabled(true);
            panel.getRemove().setEnabled(true);
            panel.hideButtons();
            getInformationAboutTask(panel, list.getValue().toString());
        } else {
            TasksCreationPanel panel = (TasksCreationPanel)event.getComponent().getParent().getParent();
            panel.getEdit().setEnabled(false);
            panel.getRemove().setEnabled(false);
            panel.hideButtons();
            panel.hideSelections();
        }
    }

    private void getInformationAboutTask(TasksCreationPanel panel, String listValue) {
        NativeSelect minutes = panel.getMinutesSelect();
        NativeSelect hours = panel.getHoursSelect();
        NativeSelect day = panel.getDaySelect();
        NativeSelect month = panel.getMonthSelect();
        NativeSelect weekday = panel.getWeekdaySelect();
        int index = getTaskIndex(listValue);
        Task task = Application.getInstance().getTasksManagerService().getTasksList().get(index);
        setNewValues(task, minutes, hours, day, month, weekday);
        panel.showSelections();
    }

    private void setNewValues(Task task, NativeSelect minutes, NativeSelect hours,
                              NativeSelect day, NativeSelect month, NativeSelect weekday) {
        minutes.setValue(task.getMinutes() == -1 ? "*" : Integer.toString(task.getMinutes()));
        hours.setValue(task.getHours() == -1 ? "*" : Integer.toString(task.getHours()));
        day.setValue(task.getDayOfMonth() == -1 ? "*" : Integer.toString(task.getDayOfMonth()));
        if (task.getDayOfMonth() == -1) {
            month.setValue("*");
        } else {
            String newMouthValue = Month.getMonthByNumber(task.getDayOfMonth()).name();
            month.setValue(newMouthValue);
        }
        weekday.setValue(task.getDayOfWeek() == -1 ? "*" : Weekday.getWeekday(task.getDayOfWeek()).name());
    }

    private int getTaskIndex(String listValue) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; listValue.charAt(i) != '.'; i++) {
            builder.append(listValue.charAt(i));
        }
        return Integer.parseInt(builder.toString()) - 1;
    }
}
