package com.exadel.dinnerorders.vaadinwindow.windows;

import com.vaadin.ui.*;

public class WelcomeWindow extends Window {
    private Label welcomeLabel;
    private Panel welcomePanel;

    public WelcomeWindow(String welcomeWindowText) {
        super();
        initComponents(welcomeWindowText);
        addComponent(welcomePanel);
    }

    private void initComponents(String welcomeWindowText) {
        welcomeLabel = new Label("<h1>Welcome! You log in as " + welcomeWindowText + "</h1>");
        welcomeLabel.setContentMode(Label.CONTENT_RAW);

        welcomePanel = new Panel();
        welcomePanel.setSizeFull();
        welcomePanel.addComponent(welcomeLabel);
        ((VerticalLayout)welcomePanel.getContent()).setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);
    }
}
