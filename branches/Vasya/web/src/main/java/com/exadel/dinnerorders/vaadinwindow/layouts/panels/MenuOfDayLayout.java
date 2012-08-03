package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Weekday;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 23.7.12
 */

public class MenuOfDayLayout extends HorizontalLayout {
    private Label weekday;
    private Button addMenuItem;
    private List<MenuItemLayout> menuItemLines;

    public MenuOfDayLayout(Weekday weekday){
        super();
        this.weekday = new Label(weekday.name());
        addMenuItem = new Button("+");
        addComponent(this.weekday);
        addComponent(addMenuItem);
        menuItemLines = new LinkedList<MenuItemLayout>();
        MenuItemLayout newMenuItemLayout = new MenuItemLayout();
        addComponent(newMenuItemLayout);
        menuItemLines.add(newMenuItemLayout);
    }

    public Weekday getWeekday() {
        return Weekday.valueOf((String) weekday.getValue());
    }

    public List<MenuItemLayout> getMenuItemLines() {
        return Collections.unmodifiableList(menuItemLines);
    }
}
