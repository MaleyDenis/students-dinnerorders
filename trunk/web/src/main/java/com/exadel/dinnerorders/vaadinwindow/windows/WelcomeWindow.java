package com.exadel.dinnerorders.vaadinwindow.windows;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEvent;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;

public class WelcomeWindow extends Window {
    private Label welcomeLabel;
    private Panel welcomePanel;

    public WelcomeWindow() {
        super();
        initComponents();
        addComponent(welcomePanel);
    }

    @Subscribe
    public void addEventsHandler(AuthenticationEvent authenticationEvent) {
        welcomeLabel.setValue("<h1>Welcome! You log in as " + Application.getInstance().getUserName() + "</h1>");
    }

    private void initComponents() {
        welcomeLabel = new Label("Welcome");
        welcomeLabel.setContentMode(Label.CONTENT_RAW);

        welcomePanel = new Panel();
        welcomePanel.setSizeFull();
        welcomePanel.addComponent(welcomeLabel);
        ((VerticalLayout)welcomePanel.getContent()).setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);
    }
}
