package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.vaadinwindow.windows.LoginWindow;
import com.vaadin.Application;

public class DinnerOrders extends Application {

    @Override
    public void init() {
        LoginWindow loginWindow = new LoginWindow();
        setMainWindow(loginWindow);
    }
}
