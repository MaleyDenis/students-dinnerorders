package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Month;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.entity.tasks.Task;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.listeners.*;
import com.vaadin.ui.*;

import java.util.List;

public class TasksCreationPanel extends Panel {
    private NativeSelect minutesSelect;
    private NativeSelect hoursSelect;
    private NativeSelect daySelect;
    private NativeSelect monthSelect;
    private NativeSelect weekdaySelect;
    private NativeSelect actionSelect;
    private GridLayout layout;
    private ListSelect tasksList;
    private Button create;
    private Button cancel;
    private Button add;
    private Button edit;
    private Button remove;
    private final Application application;

    public TasksCreationPanel(Application application) {
        super();
        this.application = application;
        setSizeFull();
        initComponents();
        locateComponents();
        alignComponents();
        hideButtons();
        hideSelections();
    }

    private void initComponents() {
        initLayout();
        initListOfTasks();
        initMinutesSelect();
        initHoursSelect();
        initDaySelect();
        initMonthSelect();
        initWeekdaySelect();
        initActionSelect();
        initButton();
    }

    private void initListOfTasks() {
        tasksList = new ListSelect("All existing tasks");
        updateList();
    }

    private void initLayout() {
        layout = new GridLayout(8, 6);
        layout.setSizeFull();
        setConstraints();
    }

    private void initMinutesSelect() {
        minutesSelect = new NativeSelect("Minute");
        minutesSelect.setNullSelectionAllowed(false);
        minutesSelect.addItem("*");
        for (int i = 0; i < DateUtils.MINUTES_IN_HOUR; i++) {
            minutesSelect.addItem(Integer.toString(i));
        }
        minutesSelect.setValue("*");
    }

    private void initHoursSelect() {
        hoursSelect = new NativeSelect("Hour");
        hoursSelect.setNullSelectionAllowed(false);
        hoursSelect.addItem("*");
        for (int i = 0; i < DateUtils.HOURS_IN_DAY; i++) {
            hoursSelect.addItem(Integer.toString(i));
        }
        hoursSelect.setValue("*");
    }

    private void initDaySelect() {
        daySelect = new NativeSelect("Day");
        daySelect.addItem("*");
        daySelect.setNullSelectionAllowed(false);
        int daysInFebruary = Month.getDaysInMonth(Month.FEBRUARY);
        for (int i = 1; i <= daysInFebruary; i++) {
            daySelect.addItem(Integer.toString(i));
        }
        daySelect.setValue("*");
    }

    private void initMonthSelect() {
        monthSelect = new NativeSelect("Month");
        monthSelect.setNullSelectionAllowed(false);
        monthSelect.addItem("*");
        for (String monthName : Month.getMonths()) {
            monthSelect.addItem(monthName);
        }
        monthSelect.addListener(new MonthSelectListener());
        monthSelect.setValue("*");
        monthSelect.setImmediate(true);
    }

    private void initWeekdaySelect() {
        weekdaySelect = new NativeSelect("Weekday");
        weekdaySelect.setNullSelectionAllowed(false);
        weekdaySelect.addItem("*");
        for (int i = 1; i <= DateUtils.DAYS_IN_WEEK; i++) {
            weekdaySelect.addItem(Weekday.getWeekday(i).name());
        }
        weekdaySelect.setValue("*");
    }

    private void initActionSelect() {
        actionSelect = new NativeSelect("Select task");
        actionSelect.addItem("Delete old menus");
        actionSelect.addItem("Delete old orders");
        actionSelect.addItem("Database auto-reimport");
        actionSelect.setNullSelectionAllowed(false);
        actionSelect.setValue("Delete old menus");
    }

    private void initButton() {
        create = new Button("Create");
        create.addListener(new CreateTaskButtonListener(application));
        cancel = new Button("Cancel");
        cancel.addListener(new CancelTaskCreationListener(application));
        add = new Button("Add");
        add.addListener(new AddTaskButtonListener());
        edit = new Button("Save new cofiguration");
        edit.setEnabled(false);
        edit.addListener(new EditTaskButtonListener(application));
        remove = new Button("Remove");
        remove.setEnabled(false);
        remove.addListener(new RemoveTaskListener(application));
    }

    private void locateComponents() {
        layout.addComponent(tasksList, 0, 1, 2, 3);
        layout.addComponent(add, 0, 4);
        layout.addComponent(edit, 1, 4);
        layout.addComponent(remove, 2, 4);
        layout.addComponent(minutesSelect, 4, 1);
        layout.addComponent(hoursSelect, 5, 1);
        layout.addComponent(daySelect, 6, 1);
        layout.addComponent(monthSelect, 7, 1);
        layout.addComponent(weekdaySelect, 4, 2);
        layout.addComponent(actionSelect, 5, 2);
        layout.addComponent(create, 4, 3);
        layout.addComponent(cancel, 5, 3);
        setContent(layout);
    }

    private void setConstraints() {
        layout.setColumnExpandRatio(0, 0.2f);
        layout.setColumnExpandRatio(1, 1f);
        layout.setColumnExpandRatio(2, 0.2f);
        layout.setColumnExpandRatio(3, 2.5f);
        layout.setColumnExpandRatio(4, 2.5f);
        layout.setColumnExpandRatio(5, 2.5f);
        layout.setColumnExpandRatio(6, 2.5f);
        layout.setColumnExpandRatio(7, 2.5f);
    }

    private void alignComponents() {
        layout.setComponentAlignment(add, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(edit, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(remove, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(minutesSelect, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(hoursSelect, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(daySelect, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(monthSelect, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(weekdaySelect, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(actionSelect, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(create, Alignment.BOTTOM_CENTER);
        layout.setComponentAlignment(cancel, Alignment.BOTTOM_CENTER);
    }

    public NativeSelect getMinutesSelect() {
        return minutesSelect;
    }

    public NativeSelect getHoursSelect() {
        return hoursSelect;
    }

    public NativeSelect getDaySelect() {
        return daySelect;
    }

    public NativeSelect getMonthSelect() {
        return monthSelect;
    }

    public NativeSelect getWeekdaySelect() {
        return weekdaySelect;
    }

    public NativeSelect getActionSelect() {
        return actionSelect;
    }

    private String getTaskName(Task task, int index) {
        String className = task.getClass().getName();
        if (className.contains("ClearMenuTableTask")) {
            return index + ". Delete old menus";
        } else if (className.contains("ClearOrderTableTask")) {
            return index + ". Delete old order";
        } else if (className.contains("ReimportDatabaseTask")) {
            return index + ". Database auto-reimport";
        }
        return index + ". Task";
    }

    public ListSelect getTasksList() {
        return tasksList;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getRemove() {
        return remove;
    }

    public void updateList() {
        tasksList.removeAllItems();
        List<Task> allTasksList = application.getTasksManagerService().getTasksList();
        int index = 1;
        for (Task task : allTasksList) {
            tasksList.addItem(getTaskName(task, index));
            index++;
        }
        tasksList.setMultiSelect(false);
        tasksList.setNullSelectionAllowed(false);
        tasksList.setWidth(100, UNITS_PERCENTAGE);
        tasksList.addListener(new TaskItemSelectListener(application));
        tasksList.setImmediate(true);
    }

    public void hideButtons() {
        create.setVisible(false);
        cancel.setVisible(false);
    }

    public void showSelections() {
        minutesSelect.setVisible(true);
        hoursSelect.setVisible(true);
        daySelect.setVisible(true);
        monthSelect.setVisible(true);
        weekdaySelect.setVisible(true);
        actionSelect.setVisible(true);
    }

    public void hideSelections() {
        minutesSelect.setVisible(false);
        hoursSelect.setVisible(false);
        daySelect.setVisible(false);
        monthSelect.setVisible(false);
        weekdaySelect.setVisible(false);
        actionSelect.setVisible(false);
    }

    public void showButtons() {
        create.setVisible(true);
        cancel.setVisible(true);
    }
}
