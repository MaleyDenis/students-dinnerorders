package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.service.MessageService;
import com.exadel.dinnerorders.vaadinwindow.layouts.MessageRow;
import com.exadel.dinnerorders.vaadinwindow.listeners.UpdateChatBoardListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;

import java.util.Collection;

public class ChatBoard extends Panel {
    private Topic topic;

    public ChatBoard() {
        super();
        initComponents();
        addMessages();
        Collection<Message> messages = MessageService.loadAll();
        addMessages(messages);
        //////////////////////////////////////////////////////////////
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(515, UNITS_PIXELS);
    }

    private void initComponents() {
        final Button refreshButton = new Button("Refresh");
        refreshButton.setVisible(true);
        refreshButton.setEnabled(true);
        refreshButton.addListener(new UpdateChatBoardListener());
        refreshButton.setParent(getContent());
    }

    private void addMessages() {
        if (topic != null) {
            for (Message message : topic.getTopicMessage()) {
                getContent().addComponent(new MessageRow(message));
            }
        }
    }

    public void addMessages(Collection<Message> messages) {
        for (Message message : messages) {
            if (message == null) {
                continue;
            }
            MessageRow messageRow = new MessageRow(message);
            addComponent(messageRow);
            fireComponentAttachEvent(messageRow);
        }
    }

    public void addMessage(Message message) {
       addComponent(new MessageRow(message));
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
        refresh();
    }

    public void refresh() {
        removeAllComponents();
        addMessages();
    }
}
