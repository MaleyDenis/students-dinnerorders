package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Content;
import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MessageService;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.UploadFinishedEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatBoard;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.CreateMessageChatPanel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class SendMessageListener implements Button.ClickListener {
    private VerticalLayout layout;

    public SendMessageListener(VerticalLayout groupLayout, EventBus bus) {
        bus.register(this);
        layout = groupLayout;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        ((Upload)layout.getComponent(2)).submitUpload();
        if (!((Upload)layout.getComponent(2)).isUploading()) {
            if (((TextArea) ((HorizontalLayout) layout.getParent()).getComponent(0)).getValue().equals("")) {
                return;
            }
            createNoContentMessage();
        }
    }

    private void createNoContentMessage() {
        ChatBoard chatBoard = (ChatBoard)((VerticalLayout)layout.getParent().getParent().getParent()).getComponent(1);
        Message message = new Message();
        message.setTopic(chatBoard.getTopic());
        message.setText((String) ((TextArea) ((HorizontalLayout) layout.getParent()).getComponent(0)).getValue());
        ((TextArea)((HorizontalLayout)layout.getParent()).getComponent(0)).setValue("");
        message.setDate(DateUtils.getCurrentTime());
        message.setUser(((WebApplicationController) chatBoard.getApplication()).getApplication().getUser());
        message.setContentList(new ArrayList<Content>());
        chatBoard.addMessage(message);
        MessageService.save(message);
    }

    @Subscribe
    public void fileUploadFinished(UploadFinishedEvent ufEvent) {
        ChatBoard chatBoard = (ChatBoard)((VerticalLayout)layout.getParent().getParent().getParent()).getComponent(1);
        Message message = ((CreateMessageChatPanel)((VerticalLayout)chatBoard.getParent()).getComponent(2)).getNewMessage();
        message.setTopic(chatBoard.getTopic());
        message.setText((String) ((TextArea) ((HorizontalLayout) layout.getParent()).getComponent(0)).getValue());
        ((TextArea)((HorizontalLayout)layout.getParent()).getComponent(0)).setValue("");
        chatBoard.addMessage(message);
        MessageService.save(message);
    }
}
