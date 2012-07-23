package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.exadel.dinnerorders.vaadinwindow.events.RemoveDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;

public class MenuCreationPanel extends Panel {
    public static final int NUMBER_OF_SERVICE_DAYS = 5;
    public static final int DEFAULT_LAYOUT_ROWS = 25;
    public static final int DEFAULT_LAYOUT_COLUMNS = 2;
    private TextField cafeName;
    private Label serviceDays;
    private Label[] weekdays;
    private Button saveButton;
    private GridLayout layout;

    public MenuCreationPanel() {
        super();
        Application.getInstance().getEventBus().register(this);
        setSizeFull();
        initComponents();
        locateComponents();
        setContent(layout);
    }

    private void initComponents() {
        initLabels();
        initTextFields();
        initButtons();
        initLayout();
    }

    private void initLabels() {
        serviceDays = new Label("<h2>Monday - Friday</h2>", Label.CONTENT_RAW);
        serviceDays.setWidth(145, UNITS_PIXELS);
        weekdays = new Label[NUMBER_OF_SERVICE_DAYS];
        for (int i = 0; i < NUMBER_OF_SERVICE_DAYS; i++) {
            weekdays[i] = new Label("<h2>" + Weekday.getWeekday(i + 1).name() + "</h2>", Label.CONTENT_RAW);
            weekdays[i].setWidth(100, UNITS_PIXELS);
        }
    }

    private void initTextFields() {
        cafeName = new TextField("Cafe name");
    }

    private void initButtons() {
        saveButton = new Button("Save");
    }

    private void initLayout() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMNS, DEFAULT_LAYOUT_ROWS);
        layout.setWidth(100, UNITS_PERCENTAGE);
        layout.setHeight(100, UNITS_PERCENTAGE);
    }

    private void locateComponents() {
        layout.addComponent(cafeName);
        layout.setComponentAlignment(cafeName, Alignment.MIDDLE_CENTER);
        layout.addComponent(serviceDays);
        layout.setComponentAlignment(serviceDays, Alignment.MIDDLE_CENTER);
        int numberOfRow = 1;
        for (int i = 0; i < NUMBER_OF_SERVICE_DAYS; i++) {
            layout.addComponent(weekdays[i], 0, numberOfRow, 1, numberOfRow++);
            layout.setComponentAlignment(weekdays[i], Alignment.MIDDLE_CENTER);
            layout.addComponent(new DishDescriptionRow(), 0, numberOfRow, 1, numberOfRow++);
        }
    }

    @Subscribe
    public void dishAdded(AddDishEvent addDishEvent) {
        GridLayout.Area area = layout.getComponentArea(addDishEvent.getParent());
        if (area == null){
            return;
        }
        int nextRow = area.getRow1() + 1;
        layout.insertRow(nextRow);
        layout.addComponent(new DishDescriptionRow(), 0, nextRow, 1, nextRow);
    }

    @Subscribe
    public void dishRemoved(RemoveDishEvent removeDishEvent) {
        GridLayout.Area area = layout.getComponentArea(removeDishEvent.getParent());
        if (area == null){
            return;
        }
        int currRow = area.getRow1();
        layout.removeRow(currRow);
    }
}
