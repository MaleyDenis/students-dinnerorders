package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 19.7.12
 */

public class CreateMenuPanel extends Panel {
    private class MenuItemLine {
        private int number;
        private TextField id;
        private TextField description;
        private TextField cost;
        private Button delete;

        public MenuItemLine(int row, int number) {
            this.number = number;
            id = new TextField();
            description = new TextField();
            cost = new TextField();
            delete = new Button("-");
            layout.addComponent(id, 0, row);
            layout.addComponent(description, 1 ,row);
            layout.addComponent(cost, 2, row);
            layout.addComponent(delete, 3, row);
        }
    }

    public static final int DEFAULT_LAYOUT_COLUMN_COUNT = 4;
    public static final int DEFAULT_LAYOUT_ROW_COUNT = 4;
    private GridLayout layout;
    private TextField menuNameTextField;
    private TextField dateStartTextField;
    private TextField dateEndTextField;
    private Label weekdayRow[];
    private Button createButton[];
    private List<MenuItemLine> menuItemLines[];

    public CreateMenuPanel(){
        super();
        Application.getInstance().getEventBus().register(this);
        initComponents();
        //initLayout();
        //locateComponents();
    }

    private void initComponents() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMN_COUNT, DEFAULT_LAYOUT_ROW_COUNT);
        menuNameTextField = new TextField("Menu name.");
        dateStartTextField = new TextField("Date start.");
        dateEndTextField = new TextField("Date end.");
        layout.addComponent(menuNameTextField, 0, 0);
        layout.addComponent(dateStartTextField, 1, 0);
        layout.addComponent(dateEndTextField, 2, 0);
        weekdayRow = new Label[7];
        createButton = new Button[7];
        menuItemLines = new ArrayList[7];
        for(int i = 0;i < 7;i++){
            weekdayRow[i] = new Label(Weekday.getWeekday(i + 1).name());
            createButton[i] = new Button("+");
            menuItemLines[i] = new ArrayList<MenuItemLine>();
            MenuItemLine newMenuItemLine = new MenuItemLine(2 + 2 * i, 0);
            menuItemLines[i].add(newMenuItemLine);
            layout.addComponent(weekdayRow[i], 0, 1 + 2 * i);
            layout.addComponent(createButton[i], 1, 1 + 2 * i);
        }
    }
}
