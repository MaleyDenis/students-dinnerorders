package com.exadel.dinnerorders.vaadinwindow.layouts;


import com.vaadin.ui.*;

public class MenuItemDescriptionRow  extends GridLayout {
    public static final int DEFAULT_ROWS = 1;
    public static final int DEFAULT_COLUMNS = 4;
    private Label dishName;
    private Label cost;
    private Double costMenuItem;
    private String description;
    private CheckBox menuItemSelect;



    public MenuItemDescriptionRow(Double cost,String description) {

        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        this.costMenuItem = cost;
        this.description = description;
        setExpandRatios();
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(40, UNITS_PIXELS);
        initComponents();
        locateComponents();
    }



    private void setExpandRatios() {
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 6f);
        setColumnExpandRatio(2, 2f);
        setColumnExpandRatio(3, 1f);
    }

    private void initComponents() {
        initTextFields();
    }

    private void initTextFields() {
        dishName = new Label(description);
        dishName.setWidth(100, UNITS_PERCENTAGE);
        dishName.setHeight(30, UNITS_PIXELS);

        cost = new Label(costMenuItem+"");
        cost.setWidth(30, UNITS_PIXELS);

        menuItemSelect = new CheckBox();
    }


    private void locateComponents() {
        FormLayout dishNameLayout = new FormLayout();
        dishNameLayout.setWidth(90, UNITS_PERCENTAGE);
        dishNameLayout.addComponent(dishName);

        FormLayout costLayout = new FormLayout();
        costLayout.setWidth(80, UNITS_PERCENTAGE);
        costLayout.addComponent(cost);

        addComponent(dishNameLayout, 1, 0);
        addComponent(costLayout, 2, 0);
        addComponent(menuItemSelect, 3, 0);

        setComponentAlignment(dishNameLayout, Alignment.MIDDLE_CENTER);
        setComponentAlignment(costLayout, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(menuItemSelect, Alignment.MIDDLE_CENTER);
    }


    public boolean isSelected(){
        return menuItemSelect.booleanValue();
    }


}
