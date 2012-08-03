package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Weekday;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

/**
 * User: Василий Силин
 * Date: 19.7.12
 */

public class CreateMenuPanel extends Panel {
    public static final int DEFAULT_LAYOUT_COLUMN_COUNT = 4;
    public static final int DEFAULT_LAYOUT_ROW_COUNT = 4;
    private TextField cafeNameTextField;
    private TextField dateStartTextField;
    private TextField dateEndTextField;
    private MenuOfDayLayout menuOfDay[];

    public CreateMenuPanel(){
        super();
        cafeNameTextField = new TextField("Cafe name.");
        dateStartTextField = new TextField("Date start.");
        dateEndTextField = new TextField("Date end.");
        addComponent(cafeNameTextField);
        addComponent(dateStartTextField);
        addComponent(dateEndTextField);
        menuOfDay = new MenuOfDayLayout[5];
        for(int i = 0;i < 5;i++){
            menuOfDay[i] = new MenuOfDayLayout(Weekday.getWeekday(i));
            addComponent(menuOfDay[i]);
        }
    }
}
