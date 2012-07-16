package com.exadel.dinnerorders.vaadinwindow.events;
import com.google.common.eventbus.Subscribe;

public interface AuthenticationEventHandler {
    @Subscribe
    public void authenticationPassed(AuthenticationEvent aEvent);
}
