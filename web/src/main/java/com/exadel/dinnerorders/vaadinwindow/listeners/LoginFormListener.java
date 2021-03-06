package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.exception.IllegalUserLoginException;
import com.exadel.dinnerorders.service.LdapService;
import com.exadel.dinnerorders.service.UserService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
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
        LdapService ldap = new LdapService();
        String login = loginEvent.getLoginParameter("username");
        User user = UserService.findUserByUserName(ldap.getUserName(login));
        if (user.getUserName().equals("Okunevich Alexandr")) {
            user.setRole(Role.ADMIN);
        }
        Application.getInstance().setUser(user);
        Application.getInstance().getEventBus().post(new AuthenticationEvent());
    }

    private boolean isInformationValid(LoginForm.LoginEvent loginEvent) {
        try {
            LdapService ldap = new LdapService();
            String login = loginEvent.getLoginParameter("username");
            String password = loginEvent.getLoginParameter("password");
            return ldap.checkUser(login, password);
        } catch (IllegalUserLoginException illegalLoginException) {
            return false;
        }
    }
}
