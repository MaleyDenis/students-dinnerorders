package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.vaadinwindow.layouts.MenuItemDescriptionRow;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ShowDayMenuPanel extends GridLayout {
    public static final int DEFAULT_COLUMNS = 1;
    public static final int DEFAULT_ROWS = 2;
    private Weekday weekday;
    private Label dayLabel;
    private Map<Weekday,List<MenuItem>> listMap;


    public ShowDayMenuPanel(Weekday weekday, Map<Weekday,List<MenuItem>> listMap) {
        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        this.listMap = listMap;
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(100, UNITS_PERCENTAGE);
        this.weekday = weekday;
        initComponents();
        locateComponents();
    }



    private void initComponents() {
        dayLabel = new Label(weekday.name());
        int symbols = ((String)dayLabel.getValue()).length();
        dayLabel.setWidth(symbols * 9, UNITS_PIXELS);
    }

    private void locateComponents(){
        addComponent(dayLabel);
        setComponentAlignment(dayLabel, Alignment.MIDDLE_CENTER);

        for (MenuItem menuItem: listMap.get(weekday))  {
            addComponent(new MenuItemDescriptionRow(menuItem.getCost(), menuItem.getDescription()));
        }
    }


    public Weekday getWeekday() {
        return weekday;
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> list = new ArrayList<MenuItem>();
        int startRow = 1;

        for (int i = startRow; i < getComponentCount(); i++) {
            MenuItemDescriptionRow row = (MenuItemDescriptionRow) getComponent(0, i);
            if (row.isSelected()) {
                list.add(listMap.get(weekday).get(i - 1));

            }
        }
        return list;
    }

}