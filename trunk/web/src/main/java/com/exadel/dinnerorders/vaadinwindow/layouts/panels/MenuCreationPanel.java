package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveMenuEvent;
import com.exadel.dinnerorders.vaadinwindow.listeners.CancelButtonListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.SaveButtonListener;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuCreationPanel extends Panel {
    public final static int MAX_CAFE_NAME_LENGTH = 25;
    public static final int NUMBER_OF_SERVICE_DAYS = 5;
    public static final int DEFAULT_LAYOUT_ROWS = 12;
    public static final int DEFAULT_LAYOUT_COLUMNS = 2;
    private TextField cafeName;
    private Label serviceDays;
    private Label[] weekdays;
    private Button saveButton;
    private Button cancelButton;
    private GridLayout layout;

    public MenuCreationPanel() {
        super();
        Application.getInstance().getEventBus().register(this);
        setWidth(90, UNITS_PERCENTAGE);
        setHeight(90, UNITS_PERCENTAGE);
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
        int lastIndex = ("DD-MM-YYYY").length();
        String mondayDate = DateUtils.getCurrentMondayDate().toString().substring(0, lastIndex);
        String fridayDate = DateUtils.getCurrentFridayDate().toString().substring(0, lastIndex);

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

    private void initButtons() {
        saveButton = new Button("Save");
        saveButton.addListener(new SaveButtonListener());
        cancelButton = new Button("Cancel");
        cancelButton.addListener(new CancelButtonListener());
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

        int insertRowNumber = 1;
        for (int i = 0; i < NUMBER_OF_SERVICE_DAYS; i++) {
            layout.addComponent(new DayMenuPanel(Weekday.getWeekday(i + 1)), 0, insertRowNumber, 1, insertRowNumber++);
            layout.addComponent(new Label("<br>", Label.CONTENT_RAW), 0, insertRowNumber, 1, insertRowNumber++);
        }

        layout.addComponent(saveButton, 0, insertRowNumber);
        layout.setComponentAlignment(saveButton, Alignment.BOTTOM_CENTER);
        layout.addComponent(cancelButton, 1, insertRowNumber);
        layout.setComponentAlignment(cancelButton, Alignment.BOTTOM_CENTER);
    }

    @Subscribe
    public void menuWasConfirmedToSave(SaveMenuEvent saveMenuEvent) {
        GridLayout eventParent = saveMenuEvent.getParent();
        if (eventParent != layout) {
            return;
        }
        cafeName.setIcon(new ExternalResource(""));
        String nameOfCafe = (String)cafeName.getValue();
        Map<Weekday, List<MenuItem>> items = new HashMap<Weekday, List<MenuItem>>();

        int startFromRow = 1;
        for (int i = 0; i < MenuCreationPanel.NUMBER_OF_SERVICE_DAYS; i++) {
            DayMenuPanel dayPanel = (DayMenuPanel)layout.getComponent(0, startFromRow);
            Weekday weekday = dayPanel.getWeekday();
            items.put(weekday, dayPanel.getMenuItems());
            startFromRow += 2;
        }

        Menu menu = new Menu(null, nameOfCafe, DateUtils.getCurrentMondayDate(),
                DateUtils.getCurrentFridayDate(), items);
        boolean result = MenuService.save(menu);
        showInformationMessage(result);

    }

    private void showInformationMessage(boolean result) {
        int displayedTime = 2000;
        String messageText = result ? "Saved OK" : "Error while saving";
        int type = result ? Window.Notification.TYPE_HUMANIZED_MESSAGE : Window.Notification.TYPE_ERROR_MESSAGE;
        Window.Notification message = new Window.Notification(messageText, type);
        message.setStyleName("mynotification.css");
        message.setDelayMsec(displayedTime);
        getApplication().getMainWindow().showNotification(message);
        flush();
    }

    public void flush() {
        cafeName.setIcon(new ExternalResource(""));
        cafeName.setValue("");

        int startFromRow = 1;
        for (int i = 0; i < MenuCreationPanel.NUMBER_OF_SERVICE_DAYS; i++) {
            DayMenuPanel dayPanel = (DayMenuPanel)layout.getComponent(0, startFromRow);
            dayPanel.flush();
            startFromRow += 2;
        }
    }
}
