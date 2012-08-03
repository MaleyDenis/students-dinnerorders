package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.listeners.LoginFormListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;

public class LoginLayout extends GridLayout {
    public static final int DEFAULT_COLUMN_COUNT = 3;
    public static final int DEFAULT_ROW_COUNT = 3;
    private LoginForm loginForm;
    private final Label emptyLabel = new Label("<br><br>", Label.CONTENT_RAW);

    public LoginLayout() {
        super(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
        setWidth("100%");
        initComponents();
        locateComponents();
        constraintRatios();
    }

    private void constraintRatios(){
        setRowExpandRatio(0, 1);
        setColumnExpandRatio(0, 1);
        setColumnExpandRatio(1, 2);
        setColumnExpandRatio(1, 2);
        setColumnExpandRatio(2, 7);
        setColumnExpandRatio(2, 7);
    }

    private void locateComponents() {
        addComponent(emptyLabel);
        addComponent(loginForm, 1, 1);

        Embedded image = new Embedded("My image", new ExternalResource(""));
        addComponent(image, 2, 2, 2, 2);
    }

    private void initComponents() {
        loginForm = new LoginForm();
        loginForm.setCaption("Enter login and password");
        loginForm.setIcon(new ThemeResource("icons/user.png"));
        loginForm.addListener(new LoginFormListener());
    }
}
