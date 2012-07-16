package com.exadel.dinnerorders.vaadinwindow.events;

import com.google.gwt.event.shared.EventHandler;

public interface AuthenticationEventHandler extends EventHandler {
    public void authenticationPassed(AuthenticationEvent aEvent);
}
