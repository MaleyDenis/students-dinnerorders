package com.exadel.dinnerorders.vaadinwindow.windows;

import com.exadel.dinnerorders.vaadinwindow.listener.LoginFormListener;
import com.vaadin.ui.*;

public class LoginWindow extends Window {
    private LoginForm loginForm;
    private GridLayout layout;

    public LoginWindow() {
        super();
        initWindowComponents();
        locateComponents();
    }

    private void locateComponents() {
        layout.addComponent(loginForm, 1, 2, 2, 2);
        setContent(layout);
    }

    private void initWindowComponents() {

        loginForm = new LoginForm();
        loginForm.setCaption("Enter login and password");
        loginForm.setWidth("100%");
        loginForm.addListener(new LoginFormListener());

        layout = new GridLayout(5, 5);
        layout.setSpacing(true);
        layout.setWidth("100%");
    }

}
