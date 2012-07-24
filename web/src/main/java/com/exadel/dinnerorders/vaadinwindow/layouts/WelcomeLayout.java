package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.UserInfoPanel;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;

public class WelcomeLayout extends GridLayout {
    public static final int DEFAULT_COLUMN_COUNT = 5;
    public static final int DEFAULT_ROW_COUNT = 6;
    private Label welcomeLabel;
    private Panel userInfoPanel;

    public WelcomeLayout() {
        super(DEFAULT_COLUMN_COUNT, DEFAULT_ROW_COUNT);
        setWidth("100%");
        initComponents();
        setLayoutRatios();
        locateComponents();
    }

    private void setLayoutRatios() {
        setColumnExpandRatio(0, 0.1f);
        setColumnExpandRatio(1, 2f);
        setColumnExpandRatio(2, 6f);
        setColumnExpandRatio(3, 2f);
        setColumnExpandRatio(4, 0.1f);

        setRowExpandRatio(0, 0.1f);
        setRowExpandRatio(1, 2f);
        setRowExpandRatio(2, 0.1f);
        setRowExpandRatio(3, 6f);
        setRowExpandRatio(4, 2f);
        setRowExpandRatio(5, 0.1f);
    }

    private void locateComponents() {
        addComponent(new Label("<br>", Label.CONTENT_RAW));
        addComponent(userInfoPanel, 3, 1, 3, 1);
        addComponent(new Label("<br>", Label.CONTENT_RAW), 0, 2);
        addComponent(welcomeLabel, 2, 1);
        setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);
        setComponentAlignment(userInfoPanel, Alignment.MIDDLE_CENTER);
    }

    @Subscribe
    public void addEventsHandler(AuthenticationEvent authenticationEvent) {
        welcomeLabel.setValue("<h1>Welcome!</h1>");
        welcomeLabel.setWidth(115, UNITS_PIXELS);
    }

    private void initComponents() {
        welcomeLabel = new Label("Welcome");
        welcomeLabel.setContentMode(Label.CONTENT_RAW);
        userInfoPanel = new UserInfoPanel();
    }
}
