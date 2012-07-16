package com.exadel.dinnerorders.vaadinwindow.listener;

import com.exadel.dinnerorders.exception.IllegalUserLoginException;
import com.exadel.dinnerorders.service.LdapService;
import com.exadel.dinnerorders.vaadinwindow.windows.WelcomeWindow;
import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Window;

public class LoginFormListener implements LoginForm.LoginListener {

    @Override
    public void onLogin(LoginForm.LoginEvent loginEvent) {
        if (isValidInformation(loginEvent)) {
            showWelcomePage(loginEvent);
        }
    }

    private void showWelcomePage(LoginForm.LoginEvent loginEvent) {
        LdapService ldap = new LdapService("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
        String login = loginEvent.getLoginParameter("username");
        String userName = ldap.getUserName(login);

        Window parentWindow = ((LoginForm)loginEvent.getSource()).getWindow();
        WelcomeWindow welcomeWindow = new WelcomeWindow(userName);
        Application application = parentWindow.getApplication();
        application.addWindow(welcomeWindow);
        application.setMainWindow(welcomeWindow);
        parentWindow.open(new ExternalResource(welcomeWindow.getURL()));
    }

    private boolean isValidInformation(LoginForm.LoginEvent loginEvent) {
        try {
            LdapService ldap = new LdapService("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
            String login = loginEvent.getLoginParameter("username");
            String password = loginEvent.getLoginParameter("password");
            return ldap.checkUser(login, password);
        } catch (IllegalUserLoginException illegalLoginException) {
            return false;
        }
    }

}
