package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SignOutEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;

import java.util.Scanner;

public class UserInfoPanel extends Panel{
    public static final int DEFAULT_LAYOUT_COLUMN_COUNT = 3;
    public static final int DEFAULT_LAYOUT_ROW_COUNT = 4;
    private Label nameLabel;
    private Label lastNameLabel;
    private Label nameCaption;
    private Label lastNameCaption;
    private Label roleCaption;
    private Label roleLabel;
    private Button signOutButton;
    private GridLayout layout;
    private Embedded userPhoto;

    public UserInfoPanel() {
        super();
        setWidth(100, UNITS_PERCENTAGE);
        Application.getInstance().getEventBus().register(this);
        setDefaultUserInfo();
        initComponents();
        initLayout();
        locateComponents();
        setComponentsSize();
    }

    private void setDefaultUserInfo() {
        userPhoto = new Embedded("", new ExternalResource("/VAADIN/themes/runo/icons/64/user.png"));
    }

    public void locateComponents() {
        layout.addComponent(userPhoto, 0, 0, 0, 2);
        layout.addComponent(nameCaption,  1, 0);
        layout.addComponent(lastNameCaption,  1, 1);
        layout.addComponent(roleCaption, 1, 2);
        layout.addComponent(nameLabel,  2, 0);
        layout.addComponent(lastNameLabel, 2, 1);
        layout.addComponent(roleLabel, 2, 2);
        layout.addComponent(signOutButton, 1, 3);
    }

    private void initLayout() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMN_COUNT, DEFAULT_LAYOUT_ROW_COUNT);
        layout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        layout.setColumnExpandRatio(0, 0.15f);
        layout.setColumnExpandRatio(1, 0.35f);
        layout.setColumnExpandRatio(2, 0.4f);
        setContent(layout);
    }

    private void initComponents() {
        initButton();
        initLabels();
    }

    private void setComponentsSize() {
        signOutButton.setWidth(100, UNITS_PERCENTAGE);
    }

    private void initLabels() {
        nameCaption = new Label("Name");
        lastNameCaption = new Label("Last name");
        roleCaption = new Label("Role");
        initUserName();
    }

    private void initButton() {
        signOutButton = new Button("Sign out");
        signOutButton.setWidth(100, UNITS_PERCENTAGE);
        signOutButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Application.getInstance().getEventBus().post(new SignOutEvent());
                Application.getInstance().setUser(null);
            }
        });
    }

    public void initUserName() {
        Scanner scanner = new Scanner(Application.getInstance().getUser().getUserName());
        lastNameLabel = new Label(scanner.next());
        nameLabel = new Label(scanner.next());
        roleLabel = new Label(Application.getInstance().getUser().getRole().name());
    }
}
