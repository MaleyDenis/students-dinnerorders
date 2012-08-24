package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;
import com.exadel.dinnerorders.vaadinwindow.events.*;
import com.exadel.dinnerorders.vaadinwindow.layouts.LoginLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.WelcomeLayout;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.*;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;

import java.util.Collection;

public class WebApplicationController extends com.vaadin.Application {
    private Application application;
    private EventBus eventBus;// = Application.getInstance().getEventBus();
    private Layout loginLayout;
    private String datePattern = "YYYY-MM-DD";

    public Application getApplication() {
        return application;
    }

    @Override
    public void init() {

        application = new Application();
        eventBus = application.getEventBus();

        createLayouts();
        createMainWindow();
        eventBus.register(this);
        setTheme("apptheme");
    }

    private void createMainWindow() {
        Window mainWindow = new Window();
        mainWindow.setContent(loginLayout);
        setMainWindow(mainWindow);
    }

    private void createLayouts() {
        loginLayout = new LoginLayout(application);
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
        Layout welcomeLayout = new WelcomeLayout(application);
        getMainWindow().setContent(welcomeLayout);
        replaceCentralPanel(new ChatPanel(getMainWindow()));
    }

    @Subscribe
    public void userSignedOut(SignOutEvent signOutEvent) {
        getMainWindow().setContent(loginLayout);
    }

    @Subscribe
    public void showAllOrders(ShowAllOrdersEvent showAllOrdersEvent) {
        TableOrderPanel tableOrderPanel = new TableOrderPanel(application);
        replaceCentralPanel(tableOrderPanel);
    }

    @Subscribe
    public void showMenuEditor(ShowMenuCreationPanelEvent smcEvent) {
        MenuCreationPanel menuCreationPanel = new MenuCreationPanel(application);
        replaceCentralPanel(menuCreationPanel);
    }

    @Subscribe
    public void currentWeekMenuSelected(ShowCurrentWeekMenuEvent weekMenuEvent){
        Collection<Menu> currentWeek  = MenuService.findMenuForCurrentWeek();
        if (currentWeek == null || currentWeek.isEmpty()) {
            getMainWindow().showNotification("Sorry,", "no any menu for this week available", Window.Notification.TYPE_HUMANIZED_MESSAGE);
            return;
        }
        String monday = DateUtils.getCurrentMondayTime().toString().substring(0, datePattern.length());
        String friday = DateUtils.getCurrentFridayTime().toString().substring(0, datePattern.length());
        SelectMenuPanel currentWeekMenusPanel = new SelectMenuPanel(currentWeek,monday,friday, application);
        replaceCentralPanel(currentWeekMenusPanel);

    }


    @Subscribe
    public void nextWeekMenuSelected(ShowNextWeekMenuEvent nwmEvent){
        Collection<Menu> nextWeek = MenuService.findMenuForNextWeek();

        if (nextWeek == null || nextWeek.isEmpty()) {
            getMainWindow().showNotification("Sorry,", "no any meny for next week available", Window.Notification.TYPE_HUMANIZED_MESSAGE);
            return;
        }
        String monday = DateUtils.getNextMondayTime().toString().substring(0, datePattern.length());
        String friday = DateUtils.getNextFridayTime().toString().substring(0, datePattern.length());
        SelectMenuPanel currentWeekMenuPanel = new SelectMenuPanel(nextWeek,monday,friday, application);
        replaceCentralPanel(currentWeekMenuPanel);
    }

    @Subscribe
    public void showUserOrder(ShowUserOrdersEvent suoEvent) {
        UserOrdersPanel showUserOrdersPanel = new UserOrdersPanel(application);
        replaceCentralPanel(showUserOrdersPanel);
    }

    @Subscribe
    public void showMadeOrderPanel(SaveMenuUserEvent showOrderUserEvent){
        ShowMadeOrderPanel showMadeOrderPanel = new ShowMadeOrderPanel(application);
        replaceCentralPanel(showMadeOrderPanel);
    }

    @Subscribe
    public void showChatPanel(ShowChatBoardEvent scbEvent) {
        replaceCentralPanel(new ChatPanel(getMainWindow()));
    }
}
