package com.exadel.dinnerorders.vaadinwindow.application;


import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.TasksManagerService;
import com.google.common.eventbus.EventBus;

public class Application {
    private EventBus eventBus = new EventBus();
    private static Application INSTANCE = new Application();
    private User user;
    /*private TasksManagerService tasksManagerService = new TasksManagerService();

    private Application() {
        tasksManagerService.start();
    }*/

    public synchronized static Application getInstance(){
        return INSTANCE;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
