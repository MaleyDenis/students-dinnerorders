package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CreateTopicPanel extends Panel {
    private VerticalLayout verticalLayout;
    private Window window;

    public CreateTopicPanel(Window window){
        super();
        this.window = window;
        initComponent();
        locateComponent();
    }

    private void initLayout(){
        verticalLayout = new VerticalLayout();
    }

    private void initComponent(){
        initLayout();
    }

    private void locateComponent(){
        verticalLayout.addComponent(new DataTopicPanel(window));
        addComponent(verticalLayout);
    }
}
