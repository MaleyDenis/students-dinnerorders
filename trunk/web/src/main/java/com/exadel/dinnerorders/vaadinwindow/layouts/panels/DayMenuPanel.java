package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.exadel.dinnerorders.vaadinwindow.events.RemoveDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import java.util.ArrayList;
import java.util.Iterator;
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

    private void setModifyingEnabled() {
        int amount = getComponentCount();
        boolean state = (getComponentCount() - 1) != 1;
        for (int i = 1; i < amount; i++) {
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
        if (area == null){
            return;
        }
        int nextRow = area.getRow1() + 1;
        insertRow(nextRow);
        addComponent(new DishDescriptionRow(), 0, nextRow);
        setModifyingEnabled();
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
    }

    public boolean isDataValid() {
        int validRows = 0;
        Iterator<Component> iterator = getComponentIterator();

        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof DishDescriptionRow) {
                if (((DishDescriptionRow)object).checkData()) {
                    validRows++;
                }
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
            String dishName = (String)row.getDishName().getValue();
            Double cost = Double.parseDouble((String)row.getCost().getValue());
            list.add(new MenuItem(null, weekday, dishName, cost));
        }
        flush();
        return list;
    }

    public void flush() {
        int startRow = 1;
        for (int i = startRow; i < getComponentCount(); i++) {
            DishDescriptionRow row = (DishDescriptionRow)getComponent(0, i);
            row.flushValues();
        }
    }
}
