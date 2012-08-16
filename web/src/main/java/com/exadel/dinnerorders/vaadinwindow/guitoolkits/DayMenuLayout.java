package com.exadel.dinnerorders.vaadinwindow.guitoolkits;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class DayMenuLayout extends GridLayout {
    public static final int DEFAULT_COLUMNS = 2;
    public static final int DEFAULT_ROWS = 1;

    private Label description;
    private Label cost;
    private String dish;
    private Double costDish;

    public DayMenuLayout(String dish, Double costDish){
        super(DEFAULT_COLUMNS,DEFAULT_ROWS);
        this.dish = dish;
        this.costDish = costDish;
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(40, UNITS_PIXELS);
        setExpandRatios();
        initComponent();
        locateComponent();
    }

    private void setExpandRatios(){
        setRowExpandRatio(0,0.1f);
        setColumnExpandRatio(0, 2f);
        setColumnExpandRatio(1, 0.5f);
    }


    public void initComponent(){
        description = new Label();
        description.setValue(dish);
        cost = new Label();
        cost.setValue(costDish);

    }

    public void locateComponent(){
        addComponent(description,0,0);
        addComponent(cost,1,0);
        setComponentAlignment(description, Alignment.MIDDLE_CENTER);
        setComponentAlignment(cost, Alignment.MIDDLE_RIGHT);

    }

}
