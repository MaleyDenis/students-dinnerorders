package com.exadel.dinnerorders.vaadinwindow;

import com.exadel.dinnerorders.vaadinwindow.listener.ButtonListener;
import com.vaadin.Application;
import com.vaadin.ui.*;

public class MyFirstVaadinApp extends Application {
    private Label label;
    private TextField textField;
    private Button button;

    @Override
    public void init() {
        Window mainWindow = new Window();
        initComponents();
        addComponents(mainWindow);
        setMainWindow(mainWindow);
    }

    private void addComponents(Window mainWindow) {
        mainWindow.addComponent(label);
        mainWindow.addComponent(textField);
        mainWindow.addComponent(button);
    }

    private void initComponents() {
        label = new Label("Hello, vaadin");
        textField = new TextField();

        button = new Button("Click here");
        button.addListener(new ButtonListener(textField, label));
    }
}
