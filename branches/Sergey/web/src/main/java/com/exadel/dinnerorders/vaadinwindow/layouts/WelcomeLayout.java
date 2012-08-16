package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.layouts.panels.NavigationPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.UserInfoPanel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class WelcomeLayout extends GridLayout {
    public static final int DEFAULT_COLUMN_COUNT = 5;
    public static final int DEFAULT_ROW_COUNT = 6;
    private NavigationPanel navigationPanel;
    private Panel userInfoPanel;

    public WelcomeLayout() {
        super(DEFAULT_COLUMN_COUNT, DEFAULT_ROW_COUNT);
        setWidth("100%");
        initComponents();
        setLayoutRatios();
        locateComponents();
        setStyleName("welcomelayout");
    }

    private void setLayoutRatios() {
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 2f);
        setColumnExpandRatio(2, 6f);
        setColumnExpandRatio(3, 2f);
        setColumnExpandRatio(4, 0.1f);

        setRowExpandRatio(0, 0.01f);
        setRowExpandRatio(1, 0.2f);
        setRowExpandRatio(2, 0.01f);
        setRowExpandRatio(3, 10f);
        setRowExpandRatio(4, 0.2f);
        setRowExpandRatio(5, 0.1f);
    }

    private void locateComponents() {
        addComponent(new Label("<br>", Label.CONTENT_RAW));
        addComponent(userInfoPanel, 3, 1, 3, 1);
        addComponent(new Label("<br>", Label.CONTENT_RAW), 0, 2);
        addComponent(navigationPanel, 2, 1);
        setComponentAlignment(navigationPanel, Alignment.TOP_CENTER);
        setComponentAlignment(userInfoPanel, Alignment.TOP_CENTER);
    }

    private void initComponents() {
        navigationPanel = new NavigationPanel();
        userInfoPanel = new UserInfoPanel();
    }
}
