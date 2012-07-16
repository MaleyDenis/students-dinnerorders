package com.exadel.dinnerorders.vaadinwindow.windows;

import com.exadel.dinnerorders.vaadinwindow.application.WebApplication;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEvent;
import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEventHandler;
import com.google.gwt.event.shared.EventBus;
import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

public class WelcomeWindow extends Window {
    private EventBus eventBus;
    private Label welcomeLabel;
    private Panel welcomePanel;

    public WelcomeWindow() {
        super();
        initComponents();
        addComponent(welcomePanel);
        addEventsHandler();
    }

    private void addEventsHandler() {
        eventBus = WebApplication.getInstance().getEventBus();
        eventBus.addHandler(AuthenticationEvent.TYPE, new AuthenticationEventHandler() {
            @Override
            public void authenticationPassed(AuthenticationEvent aEvent) {
                welcomeLabel.setValue("<h1>Welcome! You log in as " + WebApplication.getInstance().getUserName() + "</h1>");
                Application applicationController = welcomeLabel.getApplication();
                Window currentMainWindow = applicationController.getMainWindow();
                applicationController.setMainWindow(welcomeLabel.getWindow());
                currentMainWindow.open(new ExternalResource(welcomeLabel.getWindow().getURL()));
            }
        });
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
