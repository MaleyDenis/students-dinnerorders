package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEvent;
import com.exadel.dinnerorders.vaadinwindow.events.SignOutEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.LoginLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.WelcomeLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TableOrderPanel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Window;

public class WebApplicationController extends com.vaadin.Application {

    private EventBus eventBus = Application.getInstance().getEventBus();
    private Layout welcomeLayout;
    private Layout loginLayout;
    private TableOrderPanel tableOrderPanel;

    @Override
    public void init() {
        createLayouts();
        createMainWindow();
        eventBus.register(this);

    }

    private void createMainWindow() {
        Window mainWindow = new Window();
        mainWindow.setContent(loginLayout);
        setMainWindow(mainWindow);
    }

    private void createLayouts() {
        loginLayout = new LoginLayout();
        welcomeLayout = new WelcomeLayout();
        tableOrderPanel = new TableOrderPanel();
        tableOrderPanel.setHeight(100, Sizeable.UNITS_PERCENTAGE);
        eventBus.register(welcomeLayout);
    }

    @Subscribe
    public void authenticationPassed(AuthenticationEvent authenticationEvent) {
        getMainWindow().setContent(welcomeLayout);
    }

    @Subscribe
    public void userSignedOut(SignOutEvent signOutEvent) {
        getMainWindow().setContent(loginLayout);
    }

    private void replaceCentralPanel(Component newComponent) {

    }
}
