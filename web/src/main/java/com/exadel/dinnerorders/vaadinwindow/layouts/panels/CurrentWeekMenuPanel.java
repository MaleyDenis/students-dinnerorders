package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;
import com.vaadin.ui.*;



public class CurrentWeekMenuPanel extends Panel{

    public static final int NUMBER_OF_SERVICE_DAYS = 5;
    private Label serviceDays;
    private Label[] weekdays;
    private GridLayout layout;
    public static final int DEFAULT_LAYOUT_ROWS = 12;
    public static final int DEFAULT_LAYOUT_COLUMNS = 2;
    public final static int MAX_CAFE_NAME_LENGTH = 25;
    private TextField cafeName;
    private Menu menu;

    private void initLabels() {
        int lastIndex = ("DD-MM-YYYY").length();
        String mondayDate = DateUtils.getCurrentMondayTime().toString().substring(0, lastIndex);
        String fridayDate = DateUtils.getCurrentFridayTime().toString().substring(0, lastIndex);

        serviceDays = new Label("Monday - " + mondayDate + "<br>Friday - " + fridayDate, Label.CONTENT_RAW);
        serviceDays.setWidth(145, UNITS_PIXELS);
        weekdays = new Label[NUMBER_OF_SERVICE_DAYS];
        for (int i = 0; i < NUMBER_OF_SERVICE_DAYS; i++) {
            weekdays[i] = new Label(Weekday.getWeekday(i + 1).name(), Label.CONTENT_RAW);
            weekdays[i].setWidth(100, UNITS_PIXELS);
        }
    }
    private void initTextFields() {
        cafeName = new TextField("Cafe name");
        cafeName.setDescription("Input here the name of cafe");
        cafeName.setWidth(190, UNITS_PIXELS);
        cafeName.setMaxLength(MAX_CAFE_NAME_LENGTH);
        cafeName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

    }
    private void setComponentsValues(){
        cafeName.setValue("Name");

    }


    public CurrentWeekMenuPanel(){
        super();
        menu = MenuService.findMenuForCurrentWeek();
        initLabels();
        initLayout();
        initTextFields();
        setComponentsValues();
        locateComponents();

    }

    private void locateComponents() {
        layout.addComponent(cafeName);
        layout.setComponentAlignment(cafeName, Alignment.MIDDLE_CENTER);
        layout.addComponent(serviceDays);
        layout.setComponentAlignment(serviceDays, Alignment.MIDDLE_CENTER);

        int insertRowNumber = 1;
        for (int i = 0; i < NUMBER_OF_SERVICE_DAYS; i++) {
            layout.addComponent(new ShowDayMenuPanel(Weekday.getWeekday(i + 1),menu.getItems()), 0, insertRowNumber, 1, insertRowNumber++);
            layout.addComponent(new Label("<br>", Label.CONTENT_RAW), 0, insertRowNumber, 1, insertRowNumber++);
        }
        setContent(layout);
    }

    private void initLayout() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMNS, DEFAULT_LAYOUT_ROWS);
        layout.setWidth(100, UNITS_PERCENTAGE);
        layout.setHeight(100, UNITS_PERCENTAGE);
    }



}
