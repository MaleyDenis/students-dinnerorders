package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.listeners.LoginFormListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;

public class LoginLayout extends GridLayout {
    public static final int DEFAULT_COLUMN_COUNT = 3;
    public static final int DEFAULT_ROW_COUNT = 3;
    private LoginForm loginForm;
    private final Label emptyLabel = new Label("<br><br>", Label.CONTENT_RAW);
    private final Application application;

    public LoginLayout(Application application) {
        super(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        this.application = application;
        initComponents();
        locateComponents();
        constraintRatios();
        setStyleName("loginlayout");
        setSizeFull();
    }

    private void constraintRatios(){
        setRowExpandRatio(0, 1);
        setRowExpandRatio(1, 20);
        setColumnExpandRatio(0, 1);
        setColumnExpandRatio(1, 3);
        setColumnExpandRatio(2, 3);
    }

    private void locateComponents() {
        addComponent(emptyLabel, 0, 0);
        addComponent(loginForm, 1, 1);
    }

    private void initComponents() {
        loginForm = new LoginForm();
        loginForm.setCaption("Enter login and password");
        loginForm.addListener(new LoginFormListener(application));
    }
}
