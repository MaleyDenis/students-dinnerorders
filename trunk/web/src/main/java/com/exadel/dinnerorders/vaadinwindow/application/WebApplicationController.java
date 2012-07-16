package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.vaadinwindow.windows.LoginWindow;
import com.exadel.dinnerorders.vaadinwindow.windows.WelcomeWindow;
import com.vaadin.Application;

public class WebApplicationController extends Application {

    @Override
    public void init() {
        LoginWindow loginWindow = new LoginWindow();
        setMainWindow(loginWindow);
        addSubWindows();
    }

    private void addSubWindows() {
        //This method adds all of windows, that application will have
        addWindow(new WelcomeWindow());
    }
}
