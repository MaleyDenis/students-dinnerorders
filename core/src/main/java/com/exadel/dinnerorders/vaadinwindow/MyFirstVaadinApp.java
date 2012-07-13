package com.exadel.dinnerorders.vaadinwindow;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class MyFirstVaadinApp extends Application {
    @Override
    public void init() {
        Window mainWindow = new Window();
        final Label label = new Label("Hello, vaadin");
        mainWindow.addComponent(label);
        final TextField field = new TextField();
        final Button button = new Button("Click here");
        mainWindow.addComponent(field);
        mainWindow.addComponent(button);

        button.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String text = (String)field.getValue();
                label.setValue("Hello, " + text);
            }
        });


        setMainWindow(mainWindow);
    }
}
