package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEvent;
import com.exadel.dinnerorders.vaadinwindow.windows.LoginWindow;
import com.exadel.dinnerorders.vaadinwindow.windows.WelcomeWindow;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Window;

public class WebApplicationController extends com.vaadin.Application {

    private EventBus eventBus = Application.getInstance().getEventBus();
    private WelcomeWindow welcomeWindow;

    @Override
    public void init() {
        LoginWindow loginWindow = new LoginWindow();
        setMainWindow(loginWindow);
        createWindows();
        addSubWindows();
        eventBus.register(this);
    }

    private void createWindows() {
        welcomeWindow = new WelcomeWindow();
        eventBus.register(welcomeWindow);
    }

    private void addSubWindows() {
        addWindow(welcomeWindow);
    }

    @Subscribe
    public void authenticationPassed(AuthenticationEvent authenticationEvent) {
        Window currentMainWindow = getMainWindow();
        setMainWindow(welcomeWindow);
        currentMainWindow.open(new ExternalResource(welcomeWindow.getURL()));
    }
}
