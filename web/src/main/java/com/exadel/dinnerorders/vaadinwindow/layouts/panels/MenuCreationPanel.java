package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SaveMenuEvent;
import com.exadel.dinnerorders.vaadinwindow.listeners.CafeNameChangeListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.CancelButtonListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.DateBoxListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.SaveButtonListener;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuCreationPanel extends Panel {
    public static final int NUMBER_OF_SERVICE_DAYS = 5;
    public static final int DEFAULT_ITEMS_COUNT = 5;
    public static final int DEFAULT_LAYOUT_ROWS = 12;
    public static final int DEFAULT_LAYOUT_COLUMNS = 2;
    public static final String DATE_PATTERN = "YYYY-MM-DD";
    public static final int NOTIFICATION_DISPLAY_TIME = 2000;
    private Select cafeName;
    private Button saveButton;
    private Button cancelButton;
    private GridLayout layout;
    private Timestamp mondayDate;
    private Timestamp fridayDate;
    private NativeSelect dateBox;
    private int menuItemsCounter = DEFAULT_ITEMS_COUNT;

    public MenuCreationPanel() {
        super();
        Application.getInstance().getEventBus().register(this);
        setWidth(90, UNITS_PERCENTAGE);
        setHeight(90, UNITS_PERCENTAGE);
        initComponents();
        locateComponents();
        setContent(layout);
        setStyleName("menucreation");
    }

    private void initComponents() {
        initTextFields();
        initButtons();
        initDateBox();
        initLayout();
    }

    private void initDateBox() {
        dateBox = new NativeSelect("Date");
        dateBox.setImmediate(true);
        dateBox.setNewItemsAllowed(false);
        dateBox.setWriteThrough(false);
        String monday = DateUtils.getCurrentMondayTime().toString().substring(0, DATE_PATTERN.length());
        String friday = DateUtils.getCurrentFridayTime().toString().substring(0, DATE_PATTERN.length());
        dateBox.addItem("Current week: " + monday + " - " + friday);
        monday = DateUtils.getNextMondayTime().toString().substring(0, DATE_PATTERN.length());
        friday = DateUtils.getNextFridayTime().toString().substring(0, DATE_PATTERN.length());
        dateBox.addItem("Next week: " + monday + " - " + friday);
        dateBox.addListener(new DateBoxListener());
    }

    private void initTextFields() {
        cafeName = new Select("Cafe name");
        cafeName.setDescription("Input here the name of cafe");
        cafeName.setWidth(190, UNITS_PIXELS);
        cafeName.setNewItemsAllowed(true);
        cafeName.setImmediate(true);
        cafeName.addListener(new CafeNameChangeListener(this));
    }

    private void initButtons() {
        saveButton = new Button("Save");
        saveButton.addListener(new SaveButtonListener());
        cancelButton = new Button("Cancel");
        cancelButton.addListener(new CancelButtonListener());
    }

    private void initLayout() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMNS, DEFAULT_LAYOUT_ROWS);
        layout.setSizeFull();
    }

    private void locateComponents() {
        layout.addComponent(cafeName);
        layout.setComponentAlignment(cafeName, Alignment.MIDDLE_CENTER);
        layout.addComponent(dateBox);
        layout.setComponentAlignment(dateBox, Alignment.MIDDLE_CENTER);

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
        String nameOfCafe = cafeName.getValue().toString();
        Map<Weekday, List<MenuItem>> items = new HashMap<Weekday, List<MenuItem>>();

        int startFromRow = 1;
        for (int i = 0; i < MenuCreationPanel.NUMBER_OF_SERVICE_DAYS; i++) {
            DayMenuPanel dayPanel = (DayMenuPanel)layout.getComponent(0, startFromRow);
            Weekday weekday = dayPanel.getWeekday();
            items.put(weekday, dayPanel.getMenuItems());
            startFromRow += 2;
        }

        Menu menu = new Menu(null, nameOfCafe, mondayDate, fridayDate, items);
        boolean result = MenuService.merge(menu);
        showInformationMessage(result);
    }

    private void showInformationMessage(boolean result) {
        String messageText = result ? "Saved OK" : "Error while saving";
        int type = result ? Window.Notification.TYPE_HUMANIZED_MESSAGE : Window.Notification.TYPE_ERROR_MESSAGE;
        Window.Notification message = new Window.Notification(messageText, type);
        message.setDelayMsec(NOTIFICATION_DISPLAY_TIME);
        message.setStyleName("savedok");
        getApplication().getMainWindow().showNotification(message);
        flush();
    }

    public void flush() {
        cafeName.setIcon(new ExternalResource(""));
        cafeName.setValue("");
        mondayDate = null;
        fridayDate = null;
        dateBox.setValue(null);
        dateBox.setIcon(new ExternalResource(""));
        flushDayPanels();
        menuItemsCounter = DEFAULT_ITEMS_COUNT;
    }

    public void setDate(Timestamp mondayDate, Timestamp fridayDate) {
        this.mondayDate = mondayDate;
        this.fridayDate = fridayDate;
    }

    public Timestamp getMondayDate() {
        return mondayDate;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void decrementMenuItemsCount() {
        menuItemsCounter--;
    }

    public void incrementMenuItemsCount() {
        menuItemsCounter++;
    }

    public int getMenuItemsCounter(){
        return menuItemsCounter;
    }

    public Select getCafeName() {
        return cafeName;
    }

    public NativeSelect getDateBox() {
        return dateBox;
    }

    public void flushDayPanels() {
        int startFromRow = 1;
        for (int i = 0; i < MenuCreationPanel.NUMBER_OF_SERVICE_DAYS; i++) {
            DayMenuPanel dayPanel = (DayMenuPanel)layout.getComponent(0, startFromRow);
            dayPanel.flush();
            startFromRow += 2;
        }
    }
}
