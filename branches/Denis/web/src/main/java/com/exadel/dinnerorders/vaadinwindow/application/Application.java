package com.exadel.dinnerorders.vaadinwindow.application;


import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.TasksManagerService;
import com.google.common.eventbus.EventBus;

public class Application {
    private EventBus eventBus = new EventBus();
    //private static Application INSTANCE = new Application();
    private User user;
    private Order order;
    private TasksManagerService tasksManagerService; //= new TasksManagerService();

    public Application() {
        tasksManagerService = new TasksManagerService();
        tasksManagerService.start();
    }

    public synchronized Application getInstance(){
        return this;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TasksManagerService getTasksManagerService() {
        return tasksManagerService;
    }
}