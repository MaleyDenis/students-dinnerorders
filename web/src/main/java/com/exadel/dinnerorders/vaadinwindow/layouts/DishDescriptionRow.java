package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.listener.AddDishListener;
import com.exadel.dinnerorders.vaadinwindow.listener.RemoveDishListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

public class DishDescriptionRow extends GridLayout {
    public static final int DEFAULT_ROWS = 1;
    public static final int DEFAULT_COLUMNS = 5;
    private TextField dishName;
    private TextField cost;
    private Button addDishButton;
    private Button removeDishButton;

    public DishDescriptionRow() {
        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        setExpandRatios();
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(100, UNITS_PERCENTAGE);
        initComponents();
        locateComponents();
    }

    private void setExpandRatios() {
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 6f);
        setColumnExpandRatio(2, 2f);
        setColumnExpandRatio(3, 1f);
        setColumnExpandRatio(4, 1f);
    }

    private void initComponents() {
        initTextFields();
        initButtons();
    }

    private void initTextFields() {
        dishName = new TextField("Name of dish");
        dishName.setWidth(100, UNITS_PERCENTAGE);
        cost = new TextField("Cost");
        cost.setDescription("Enter cost of the dish here");
    }

    private void initButtons() {
        addDishButton = new Button();
        addDishButton.setDescription("Add one more dish");
        addDishButton.addListener(new AddDishListener());
        addDishButton.setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/document-add.png"));

        removeDishButton = new Button();
        removeDishButton.setDescription("Remove dish");
        removeDishButton.addListener(new RemoveDishListener());
        removeDishButton.setIcon(new ExternalResource("/VAADIN/themes/runo/icons/16/document-delete.png"));
    }

    private void locateComponents() {
        addComponent(dishName, 1, 0);
        addComponent(cost, 2, 0);
        addComponent(addDishButton, 3, 0);
        addComponent(removeDishButton, 4, 0);
        setComponentAlignment(dishName, Alignment.MIDDLE_CENTER);
        setComponentAlignment(cost, Alignment.MIDDLE_CENTER);
        setComponentAlignment(addDishButton, Alignment.BOTTOM_CENTER);
        setComponentAlignment(removeDishButton, Alignment.BOTTOM_CENTER);
    }

    public Button getRemoveDishButton() {
        return removeDishButton;
    }
}
