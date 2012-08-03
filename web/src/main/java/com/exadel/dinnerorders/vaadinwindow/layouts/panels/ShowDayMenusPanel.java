package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Weekday;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class ShowDayMenusPanel extends GridLayout {

    public static final int DEFAULT_COLUMNS = 4;
    public static final int DEFAULT_ROWS = 1;

    private Label description;
    private Label cost;
    private CheckBox menuItemSelect;
    private String dish;
    private Double costDish;
    private Weekday weekday;
    private Long id;

    public ShowDayMenusPanel(String dish,Double costDish){
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
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 6f);
        setColumnExpandRatio(2, 2f);
        setColumnExpandRatio(3, 1f);
    }


    public void initComponent(){
        menuItemSelect = new CheckBox();
        description = new Label();
        description.setValue(dish);
        cost = new Label();
        cost.setValue(costDish);

    }

    public void locateComponent(){
        addComponent(description,1,0);
        addComponent(cost,2,0);
        addComponent(menuItemSelect,3, 0);
        setComponentAlignment(description, Alignment.MIDDLE_CENTER);
        setComponentAlignment(cost, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(menuItemSelect, Alignment.MIDDLE_CENTER);

    }

    public boolean isSelected(){
        return menuItemSelect.booleanValue();

    }
    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public String getDish(){
        return dish;
    }

    public Double getCostDish(){
        return costDish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
