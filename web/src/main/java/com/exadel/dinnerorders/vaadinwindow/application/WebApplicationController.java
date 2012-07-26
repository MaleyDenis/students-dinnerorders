package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.vaadinwindow.events.*;
import com.exadel.dinnerorders.vaadinwindow.layouts.LoginLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.WelcomeLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TableOrderPanel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;

public class WebApplicationController extends com.vaadin.Application {
    private EventBus eventBus = Application.getInstance().getEventBus();
    private Layout welcomeLayout;
    private Layout loginLayout;
    private TableOrderPanel tableOrderPanel;
    private MenuCreationPanel menuCreationPanel;

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
    }

    private void replaceCentralPanel(Component newComponent) {
        newComponent.setWidth(90, Sizeable.UNITS_PERCENTAGE);
        ((GridLayout)getMainWindow().getContent()).removeComponent(2, 3);
        ((GridLayout)getMainWindow().getContent()).addComponent(newComponent, 2, 3, 2, 4);
        ((GridLayout)getMainWindow().getContent()).setComponentAlignment(newComponent, Alignment.TOP_CENTER);
    }

    /////====================EventBus listeners===========================/////

    @Subscribe
    public void authenticationPassed(AuthenticationEvent authenticationEvent) {
        welcomeLayout = new WelcomeLayout();
        getMainWindow().setContent(welcomeLayout);
    }

    @Subscribe
    public void userSignedOut(SignOutEvent signOutEvent) {
        getMainWindow().setContent(loginLayout);
    }

    @Subscribe
    public void showUserOrders(ShowAllOrdersEvent showAllOrdersEvent) {
        tableOrderPanel = new TableOrderPanel();
        replaceCentralPanel(tableOrderPanel);
    }

    @Subscribe
    public void showMenuEditor(ShowMenuCreationPanelEvent smcEvent) {
        menuCreationPanel = new MenuCreationPanel();
        replaceCentralPanel(menuCreationPanel);
    }

    @Subscribe
    public void currentWeekMenuSelected(ShowCurrentWeekMenuEvent cwmEvent) {
       //CurrentWeekMenuPanel currentWeekMenuPanel = new C
    }

    @Subscribe
    public void nextWeekMeuSelected(ShowNextWeekMenuEvent nwmEvent){
    }

    @Subscribe
    public void showUserOrder(ShowUserOrdersEvent suoEvent) {
    }
}
