package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


public class ShowMadeOrderPanel extends Panel {
    private Label orderLabel;
    private VerticalLayout verticalLayout;

    public ShowMadeOrderPanel(){

    }

    public void initLabel(){
        orderLabel = new Label("Your order");

    }

    public void  initVerticalLayout(){
        verticalLayout = new VerticalLayout();
    }

    public void showMyOrder(){

    }



}
