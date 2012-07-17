package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SignOutEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

public class UserInfoPanel extends Panel {
    public static final int DEFAULT_LAYOUT_COLUMN_COUNT = 20;
    public static final int DEFAULT_LAYOUT_ROW_COUNT = 20;
    private Label nameLabel;
    private Label lastNameLabel;
    private Label nameCaption;
    private Label lastNameCaption;
    private Button signOutButton;
    private String userName;
    private String userLastName;
    private GridLayout layout;

    public UserInfoPanel() {
        super();
        initComponents();
        initLayout();
        locateComponents();
    }

    private void locateComponents() {
        layout.addComponent(new Embedded("", new ExternalResource("/VAADIN/themes/runo/icons/64/user.png")), 1, 1, 4, 9);
        layout.addComponent(nameCaption,  5, 2, 10, 2);
        layout.addComponent(lastNameCaption,  5, 6, 10, 6);
        layout.addComponent(nameLabel,  11, 2, 19, 2);
        layout.addComponent(lastNameLabel,  11, 6, 19, 6);
        layout.addComponent(signOutButton, 5, 8, 19, 18);
    }

    private void initLayout() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMN_COUNT, DEFAULT_LAYOUT_ROW_COUNT);
        layout.setWidth(100, UNITS_PERCENTAGE);
        setContent(layout);
    }

    private void initComponents() {
        getUserInfo();
        initButton();
        initLabels();
    }

    private void initLabels() {
        nameCaption = new Label("Name");
        lastNameCaption = new Label("LastName");
        nameLabel = new Label(userName);
        lastNameLabel = new Label(userLastName);
    }

    private void initButton() {
        signOutButton = new Button("Sign out");
        signOutButton.setWidth(100, UNITS_PERCENTAGE);
        signOutButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Application.getInstance().getEventBus().post(new SignOutEvent());
            }
        });
    }

    private void getUserInfo() {
        userName = "userName";
        userLastName = "userLastName";
    }
}
