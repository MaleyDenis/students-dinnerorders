package com.exadel.dinnerorders.vaadinwindow.application;


import com.google.common.eventbus.EventBus;

public class Application {
    private EventBus eventBus = new EventBus();
    private String userName;
    private static Application INSTANCE = new Application();

    private Application() {
    }

    public synchronized static Application getInstance(){
        return INSTANCE;
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
