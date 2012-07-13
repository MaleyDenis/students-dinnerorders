package com.exadel.dinnerorders.vaadinwindow.listener;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class ButtonListener implements Button.ClickListener {
    private TextField textField;
    private Label label;

    public ButtonListener(TextField textField, Label label) {
        this.textField = textField;
        this.label = label;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String text = (String)textField.getValue();
        label.setValue("Hello, " + text);
    }
}
