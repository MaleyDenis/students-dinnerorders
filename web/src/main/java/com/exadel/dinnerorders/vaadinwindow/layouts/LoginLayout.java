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
        setColumnExpandRatio(1, 3);
        setColumnExpandRatio(2, 3);
    }

    private void locateComponents() {
        addComponent(emptyLabel);
        addComponent(loginForm, 1, 1);

        String resourceURL = "http://receptic-bonappetit.ru/wp-content/uploads/2012/01/412.jpg";
        Embedded image = new Embedded("", new ExternalResource(resourceURL));
        addComponent(image, 2, 1);
    }

    private void initComponents() {
        loginForm = new LoginForm();
        loginForm.setCaption("Enter login and password");
        loginForm.setIcon(new ThemeResource("icons/user.png"));
        loginForm.addListener(new LoginFormListener());
    }
}
