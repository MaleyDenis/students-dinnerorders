package com.exadel.dinnerorders.vaadinwindow.listener;

import com.exadel.dinnerorders.exception.IllegalUserLoginException;
import com.exadel.dinnerorders.service.LdapService;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplication;
import com.exadel.dinnerorders.vaadinwindow.events.AuthenticationEvent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Window;

public class LoginFormListener implements LoginForm.LoginListener {

    @Override
    public void onLogin(LoginForm.LoginEvent loginEvent) {
        if (isInformationValid(loginEvent)) {
            showWelcomePage(loginEvent);
        } else {
            clearForm(loginEvent);
        }
    }

    private void clearForm(LoginForm.LoginEvent loginEvent) {
        LoginForm loginForm = (LoginForm)loginEvent.getSource();
        loginForm.setCaption("Incorrect login or password");
        Window.Notification warning = new Window.Notification("Mistake!", "Incorrect login or password", Window.Notification.TYPE_WARNING_MESSAGE);
        loginForm.getWindow().showNotification(warning);
    }

    private void showWelcomePage(LoginForm.LoginEvent loginEvent) {
        LdapService ldap = new LdapService("ldap://ldap.eltegra.by:389/dc=exadel,dc=com");
        String login = loginEvent.getLoginParameter("username");
        WebApplication.getInstance().setUserName(ldap.getUserName(login));
        WebApplication.getInstance().getEventBus().post(new AuthenticationEvent());
    }

    private boolean isInformationValid(LoginForm.LoginEvent loginEvent) {
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
