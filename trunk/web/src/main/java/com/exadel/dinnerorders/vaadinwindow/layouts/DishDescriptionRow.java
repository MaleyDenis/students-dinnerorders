package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.listeners.AddDishListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.CostChangedListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.RemoveDishListener;
import com.exadel.dinnerorders.vaadinwindow.listeners.SkipBoxListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

public class DishDescriptionRow extends GridLayout {
    public static final int DEFAULT_ROWS = 1;
    public static final int DEFAULT_COLUMNS = 7;
    private TextArea dishName;
    private TextField cost;
    private Embedded add;
    private Embedded remove;
    private Embedded nameStatus;
    private Embedded costStatus;
    private CheckBox skipBox;

    public DishDescriptionRow() {
        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        setExpandRatios();
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(80, UNITS_PERCENTAGE);
        initComponents();
        locateComponents();
        alignComponents();
    }

    private void alignComponents() {
        setComponentAlignment(nameStatus, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(costStatus, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(add, Alignment.TOP_CENTER);
        setComponentAlignment(remove, Alignment.TOP_LEFT);
        setComponentAlignment(skipBox, Alignment.MIDDLE_LEFT);
    }

    private void setExpandRatios() {
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 3f);
        setColumnExpandRatio(2, 0.1f);
        setColumnExpandRatio(3, 1f);
        setColumnExpandRatio(4, 0.2f);
        setColumnExpandRatio(5, 0.2f);
        setColumnExpandRatio(6, 0.2f);
    }

    private void initComponents() {
        initTextFields();
        initButtons();
    }

    private void initTextFields() {
        dishName = new TextArea("Name of dish");
        dishName.setWidth(100, UNITS_PERCENTAGE);
        dishName.setHeight(50, UNITS_PIXELS);
        dishName.setDescription("Input here name of dish");

        cost = new TextField("Cost", "0");
        cost.setDescription("Enter cost of the dish here");
        cost.setWidth(100, UNITS_PERCENTAGE);
        cost.setMaxLength(20);
        cost.addListener(new CostChangedListener());
        cost.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        nameStatus = new Embedded();
        nameStatus.setWidth(16, UNITS_PIXELS);
        nameStatus.setHeight(16, UNITS_PIXELS);

        costStatus = new Embedded();
        costStatus.setWidth(16, UNITS_PIXELS);
        costStatus.setHeight(16, UNITS_PIXELS);
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

        skipBox = new CheckBox("Skip");
        skipBox.setImmediate(true);
        skipBox.addListener(new SkipBoxListener());
    }

    private void locateComponents() {
        FormLayout dishNameLayout = new FormLayout();
        dishNameLayout.setWidth(90, UNITS_PERCENTAGE);
        dishNameLayout.addComponent(dishName);

        FormLayout costLayout = new FormLayout();
        costLayout.setWidth(80, UNITS_PERCENTAGE);
        costLayout.addComponent(cost);

        addComponent(nameStatus);
        addComponent(dishNameLayout);
        addComponent(costStatus);
        addComponent(costLayout);
        addComponent(skipBox);
        addComponent(add);
        addComponent(remove);

        setComponentAlignment(dishNameLayout, Alignment.MIDDLE_CENTER);
        setComponentAlignment(costLayout, Alignment.MIDDLE_LEFT);
    }

    public TextArea getDishName() {
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

    public boolean checkData() {
        boolean isDishNameValid = checkDishName();
        boolean isCostValid = checkCost();
        return skipBox.booleanValue() || (isDishNameValid && isCostValid);
    }

    private boolean checkDishName() {
        if (dishName.getValue().equals("")) {
            nameStatus.setSource(new ExternalResource("/VAADIN/themes/runo/icons/16/error.png"));
            return false;
        } else {
            nameStatus.setSource(new ExternalResource("/VAADIN/themes/runo/icons/16/ok.png"));
            return true;
        }
    }

    private boolean checkCost() {
        if (cost.getValue().equals("0")) {
            costStatus.setSource(new ExternalResource("/VAADIN/themes/runo/icons/16/error.png"));
            return false;
        } else {
            costStatus.setSource(new ExternalResource("/VAADIN/themes/runo/icons/16/ok.png"));
            return true;
        }
    }

    public Embedded getCostStatus() {
        return costStatus;
    }

    public Embedded getDishNameStatus() {
        return nameStatus;
    }

    public void flushValues() {
        cost.setValue("0");
        cost.setEnabled(true);
        costStatus.setSource(new ExternalResource(""));
        costStatus.setEnabled(true);
        dishName.setValue("");
        dishName.setEnabled(true);
        nameStatus.setSource(new ExternalResource(""));
        nameStatus.setEnabled(true);
        skipBox.setValue(false);
        add.setEnabled(true);
        remove.setEnabled(false);
    }

    public CheckBox getSkipBox() {
        return skipBox;
    }
}
