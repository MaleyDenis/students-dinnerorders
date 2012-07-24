package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.listeners.AddDishListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.CostChangedListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.RemoveDishListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

public class DishDescriptionRow extends GridLayout {
    public static final int DEFAULT_ROWS = 1;
    public static final int DEFAULT_COLUMNS = 5;
    private TextField dishName;
    private TextField cost;
    private Embedded add;
    private Embedded remove;
    private Embedded status;

    public DishDescriptionRow() {
        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        setExpandRatios();
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(40, UNITS_PIXELS);
        initComponents();
        locateComponents();
    }

    private void setExpandRatios() {
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 3f);
        setColumnExpandRatio(2, 1f);
        setColumnExpandRatio(3, 0.2f);
        setColumnExpandRatio(4, 0.2f);
    }

    private void initComponents() {
        initTextFields();
        initButtons();
    }

    private void initTextFields() {
        dishName = new TextField("Name of dish");
        dishName.setWidth(100, UNITS_PERCENTAGE);
        dishName.setDescription("Input here name of dish");
        cost = new TextField("Cost", "0");
        cost.setDescription("Enter cost of the dish here");
        cost.setWidth(100, UNITS_PERCENTAGE);
        cost.addListener(new CostChangedListener());
        cost.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        status = new Embedded();
        status.setWidth(16, UNITS_PIXELS);
        status.setHeight(16, UNITS_PIXELS);
    }

    private void initButtons() {
        add = new Embedded("", new ExternalResource("/VAADIN/themes/runo/icons/16/document-add.png"));
        remove = new Embedded("", new ExternalResource("/VAADIN/themes/runo/icons/16/document-delete.png"));
        add.setWidth(16, UNITS_PIXELS);
        add.setHeight(16, UNITS_PIXELS);
        add.setDescription("Add one more dish");

        remove.setWidth(16, UNITS_PIXELS);
        remove.setHeight(16, UNITS_PIXELS);
        remove.setDescription("Remove dish from menu");
        add.addListener(new AddDishListener());
        remove.addListener(new RemoveDishListener());
    }

    private void locateComponents() {
        FormLayout dishNameLayout = new FormLayout();
        dishNameLayout.setWidth(90, UNITS_PERCENTAGE);
        FormLayout costLayout = new FormLayout();
        costLayout.setWidth(80, UNITS_PERCENTAGE);
        dishNameLayout.addComponent(dishName);
        costLayout.addComponent(cost);
        addComponent(status, 0, 0);
        setComponentAlignment(status, Alignment.MIDDLE_RIGHT);
        addComponent(dishNameLayout, 1, 0);
        setComponentAlignment(dishNameLayout, Alignment.MIDDLE_CENTER);
        addComponent(costLayout, 2, 0);
        setComponentAlignment(costLayout, Alignment.MIDDLE_LEFT);
        addComponent(add, 3, 0);
        setComponentAlignment(add, Alignment.TOP_CENTER);
        addComponent(remove, 4, 0);
        setComponentAlignment(remove, Alignment.TOP_LEFT);
    }

    public TextField getDishName() {
        return dishName;
    }

    public TextField getCost() {
        return cost;
    }

    public Embedded getAdd() {
        return add;
    }

    public Embedded getRemove() {
        return remove;
    }

    public Embedded getStatus() {
        return status;
    }

    public boolean checkData() {
        if (dishName.getValue().equals("") || Integer.parseInt((String)cost.getValue()) == 0) {
            status.setSource(new ExternalResource("/VAADIN/themes/runo/icons/16/error.png"));
            return false;
        } else {
            status.setSource(new ExternalResource("/VAADIN/themes/runo/icons/16/ok.png"));
            return true;
        }
    }
}
