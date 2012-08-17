package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.service.LdapService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.SignOutEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.util.Scanner;

public class UserInfoPanel extends Panel{
    public static final int DEFAULT_LAYOUT_COLUMN_COUNT = 3;
    public static final int DEFAULT_LAYOUT_ROW_COUNT = 4;
    public static final String PHOTO_EXTENSION = ".jpeg";
    public static final String NO_PHOTO_URI = "Empty.jpeg";
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
        setHeight(100, UNITS_PIXELS);
        Application.getInstance().getEventBus().register(this);
        setDefaultUserInfo();
        initComponents();
        initLayout();
        locateComponents();
        setComponentsSize();
    }

    private void setDefaultUserInfo() {
        LdapService ldapService = new LdapService();
        int width = 70;
        int height = 89;
        String photoURI = ldapService.loadUserPhotoURI(Application.getInstance().getUser().getUserName()) + PHOTO_EXTENSION;
        if (photoURI.equals(NO_PHOTO_URI)) {
            photoURI = "/VAADIN/themes/runo/icons/64/user.png";
            width = height = 64;
        }
        userPhoto = new Embedded(null, new ExternalResource(photoURI));
        userPhoto.setWidth(width, UNITS_PIXELS);
        userPhoto.setHeight(height, UNITS_PIXELS);
    }

    public void locateComponents() {
        layout.addComponent(userPhoto, 0, 0, 0, 3);
        layout.setComponentAlignment(userPhoto, Alignment.MIDDLE_CENTER);
        layout.addComponent(nameCaption,  1, 0);
        layout.addComponent(lastNameCaption,  1, 1);
        layout.addComponent(roleCaption, 1, 2);
        layout.addComponent(nameLabel,  2, 0);
        layout.addComponent(lastNameLabel, 2, 1);
        layout.addComponent(roleLabel, 2, 2);
        layout.addComponent(signOutButton, 1, 3, 2, 3);
        layout.setComponentAlignment(signOutButton, Alignment.MIDDLE_LEFT);
    }

    private void initLayout() {
        layout = new GridLayout(DEFAULT_LAYOUT_COLUMN_COUNT, DEFAULT_LAYOUT_ROW_COUNT);
        layout.setSizeFull();
        layout.setColumnExpandRatio(0, 0.15f);
        layout.setColumnExpandRatio(1, 0.35f);
        layout.setColumnExpandRatio(2, 0.5f);
        layout.setStyleName("userinfo");
        setContent(layout);
    }

    private void initComponents() {
        initButton();
        initLabels();
    }

    private void setComponentsSize() {
        signOutButton.setWidth(80, UNITS_PERCENTAGE);
        signOutButton.setStyleName(Reindeer.BUTTON_SMALL);
    }

    private void initLabels() {
        nameCaption = new Label("Name");
        nameCaption.setStyleName("userinfocaption");
        lastNameCaption = new Label("Last name");
        lastNameCaption.setStyleName("userinfocaption");
        roleCaption = new Label("Role");
        roleCaption.setStyleName("userinfocaption");
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
        lastNameLabel.setStyleName("userinfo");
        nameLabel = new Label(scanner.next());
        nameLabel.setStyleName("userinfo");
        roleLabel = new Label(Application.getInstance().getUser().getRole().name());
        roleLabel.setStyleName("userinfo");
    }
}
