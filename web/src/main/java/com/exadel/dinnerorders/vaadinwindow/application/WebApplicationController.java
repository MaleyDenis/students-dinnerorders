package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.service.MenuService;
import com.exadel.dinnerorders.vaadinwindow.events.*;
import com.exadel.dinnerorders.vaadinwindow.layouts.LoginLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.WelcomeLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.CurrentWeekMenusPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ShowMadeOrderPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.TableOrderPanel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;

import java.util.Collection;

public class WebApplicationController extends com.vaadin.Application {
    private EventBus eventBus = Application.getInstance().getEventBus();
    private Layout welcomeLayout;
    private Layout loginLayout;

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
    public void showAllOrders(ShowAllOrdersEvent showAllOrdersEvent) {
        TableOrderPanel tableOrderPanel = new TableOrderPanel();
        replaceCentralPanel(tableOrderPanel);
    }

    @Subscribe
    public void showMenuEditor(ShowMenuCreationPanelEvent smcEvent) {
        MenuCreationPanel menuCreationPanel = new MenuCreationPanel();
        replaceCentralPanel(menuCreationPanel);
    }

    @Subscribe
    public void currentWeekMenuSelected(ShowCurrentWeekMenuEvent weekMenuEvent){
        Collection<Menu> currentWeek  = MenuService.findMenuForCurrentWeek();
        if (currentWeek == null || currentWeek.isEmpty()) {
            getMainWindow().showNotification("Sorry,", "no any menu for this week available", Window.Notification.TYPE_HUMANIZED_MESSAGE);
            return;
        }
        CurrentWeekMenusPanel currentWeekMenusPanel = new CurrentWeekMenusPanel(currentWeek);
        replaceCentralPanel(currentWeekMenusPanel);

    }


    @Subscribe
    public void nextWeekMenuSelected(ShowNextWeekMenuEvent nwmEvent){
        Collection<Menu> nextWeek = MenuService.findMenuForNextWeek();
        if (nextWeek == null || nextWeek.isEmpty()) {
            getMainWindow().showNotification("Sorry,", "no any meny for next week available", Window.Notification.TYPE_HUMANIZED_MESSAGE);
            return;
        }
        CurrentWeekMenusPanel currentWeekMenuPanel = new CurrentWeekMenusPanel(nextWeek);
        replaceCentralPanel(currentWeekMenuPanel);
    }

    @Subscribe
    public void showUserOrder(ShowUserOrdersEvent suoEvent) {
    }

    @Subscribe
    public void showMadeOrderPanel(ShowMadeOrderEvent showMadeOrderEvent){
        ShowMadeOrderPanel showMadeOrderPanel = new ShowMadeOrderPanel();
        replaceCentralPanel(showMadeOrderPanel);
    }
}
