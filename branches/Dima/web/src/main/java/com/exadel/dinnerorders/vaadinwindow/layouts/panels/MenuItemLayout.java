package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

/**
 * User: Василий Силин
 * Date: 23.7.12
 */

public class MenuItemLayout extends HorizontalLayout {
    private CheckBox correctly;
    private TextField description;
    private TextField cost;
    private Button delete;

    public MenuItemLayout(){
        super();
        correctly = new CheckBox();
        description = new TextField();
        cost = new TextField();
        delete = new Button("-");
        delete.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
            }
        });
        addComponent(correctly);
        addComponent(description);
        addComponent(cost);
        addComponent(delete);
    }

    public boolean isCorrectly() {
        return correctly.booleanValue();
    }

    public String getDescription() {
        return (String) description.getData();
    }

    public double getCost() {
        return Double.parseDouble((String) cost.getValue());
    }
}
