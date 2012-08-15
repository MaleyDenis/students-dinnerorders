package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.exadel.dinnerorders.vaadinwindow.events.RemoveDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import java.util.ArrayList;
import java.util.List;

public class DayMenuPanel extends GridLayout {
    public static final int DEFAULT_COLUMNS = 1;
    public static final int DEFAULT_ROWS = 2;
    private Weekday weekday;
    private Label dayLabel;

    public DayMenuPanel(Weekday weekday) {
        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(100, UNITS_PERCENTAGE);
        Application.getInstance().getEventBus().register(this);
        this.weekday = weekday;
        initComponents();
        locateComponents();
        setModifyingEnabled();
    }

    public void setModifyingEnabled() {
        int amount = getComponentCount();
        int skipped = 0;
        for (int i = 1; i < amount; i++) {
            if ( ((DishDescriptionRow)getComponent(0, i)).getSkipBox().booleanValue() ) {
                skipped++;
            }
        }

        boolean state = (getComponentCount() - skipped - 1) > 1;
        for (int i = 1; i < amount; i++) {
            if ( ((DishDescriptionRow)getComponent(0, i)).getSkipBox().booleanValue() ) {
                continue;
            }
            ((DishDescriptionRow)getComponent(0, i)).getRemove().setEnabled(state);
        }
    }

    private void initComponents() {
        dayLabel = new Label(weekday.name());
        int symbols = ((String)dayLabel.getValue()).length();
        dayLabel.setWidth(symbols * 9, UNITS_PIXELS);
    }

    private void locateComponents(){
        addComponent(dayLabel);
        setComponentAlignment(dayLabel, Alignment.MIDDLE_CENTER);
        addComponent(new DishDescriptionRow());
    }

    @Subscribe
    public void dishAdded(AddDishEvent addDishEvent) {
        DishDescriptionRow dishRow = ((DishDescriptionRow)addDishEvent.getParent());
        if (dishRow.getParent() != this) {
            return;
        }
        GridLayout.Area area = getComponentArea(dishRow);
        int nextRow;
        if (area == null){
            nextRow = getComponentCount();
            dishRow.setParent(null);
        } else {
            nextRow = area.getRow1() + 1;
        }
        insertRow(nextRow);
        addComponent(addDishEvent.getAddedDish(), 0, nextRow);
        setModifyingEnabled();

        MenuCreationPanel menuCreationPanel = (MenuCreationPanel)getParent().getParent();
        menuCreationPanel.incrementMenuItemsCount();
    }

    @Subscribe
    public void dishRemoved(RemoveDishEvent removeDishEvent) {
        DishDescriptionRow dishRow = ((DishDescriptionRow)removeDishEvent.getParent());
        if (dishRow.getParent() != this) {
            return;
        }
        GridLayout.Area area = getComponentArea(removeDishEvent.getParent());
        if (area == null) {
            return;
        }
        int currRow = area.getRow1();
        removeRow(currRow);
        setModifyingEnabled();
        MenuCreationPanel menuCreationPanel = (MenuCreationPanel)getParent().getParent();
        menuCreationPanel.decrementMenuItemsCount();
    }

    public boolean isDataValid() {
        int validRows = 0;
        int startRow = 1;

        for (int i = startRow; i < getComponentCount(); i++) {
            DishDescriptionRow row = (DishDescriptionRow)getComponent(0, i);
            if (row.checkData()) {
                validRows++;
            }
        }
        return validRows == getComponentCount() - 1;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> list = new ArrayList<MenuItem>();
        int startRow = 1;

        for (int i = startRow; i < getComponentCount(); i++) {
            DishDescriptionRow row = (DishDescriptionRow)getComponent(0, i);
            if (row.getSkipBox().booleanValue()) {
                row.flushValues();
                continue;
            }
            String dishName = (String)row.getDishName().getValue();
            Double cost = Double.parseDouble(row.getCost().getValue().toString());
            list.add(new MenuItem(null, weekday, dishName, cost));
        }
        flush();
        return list;
    }

    public void flush() {
        int startRow = 1;
        int leftComponentsCount = 2;
        int nComponents = getComponentCount();
        for (int i = nComponents; i >= leftComponentsCount; i--) {
            removeComponent(0, i);
        }

        DishDescriptionRow row = (DishDescriptionRow) getComponent(0, startRow);
        row.flushValues();

    }
}
