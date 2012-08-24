package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.service.MessageService;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatBoard;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import java.util.Collection;

public class UpdateChatBoardListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent event) {
        VerticalLayout layout = (VerticalLayout)event.getButton().getParent().getParent().getParent().getParent();
        ChatBoard chatBoard = (ChatBoard)(layout).getComponent(1);
        Collection<Message> messages = MessageService.loadAll();
        chatBoard.addMessages(messages);
    }
}
