package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.vaadin.ui.Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshBoardListener implements ActionListener {
    private final Button button;
    public RefreshBoardListener(Button refreshButton) {
        button = refreshButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button.click();
    }
}
