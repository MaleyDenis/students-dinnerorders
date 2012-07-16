package com.exadel.dinnerorders.vaadinwindow.application;


import com.google.common.eventbus.EventBus;

public class WebApplication {
    private EventBus eventBus;
    private String userName;
    private static WebApplication selfReference = null;

    private WebApplication() {
        eventBus = new EventBus();
        selfReference = this;
    }

    public synchronized static WebApplication getInstance(){
        if (selfReference == null) {
            new WebApplication();
        }
        return selfReference;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public String getUserName() {
        return userName;
    }
}
