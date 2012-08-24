package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.service.MessageService;
import com.exadel.dinnerorders.vaadinwindow.layouts.MessageRow;
import com.exadel.dinnerorders.vaadinwindow.listeners.UpdateChatBoardListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;

import java.util.ArrayList;
import java.util.Collection;

public class ChatBoard extends Panel {
    private Topic topic;
    //  private Timer timer;
    private Collection<MessageRow> addedMessages;

    public ChatBoard() {
        super();
        initComponents();
        addMessages();
        addedMessages = new ArrayList<MessageRow>();
        Collection<Message> messages = MessageService.loadAll();
        addMessages(messages);
        //////////////////////////////////////////////////////////////
        setWidth(100, UNITS_PERCENTAGE);
        setHeight(515, UNITS_PIXELS);
        //timer.start();
    }

    private void initComponents() {
        final Button refreshButton = new Button("Refresh");
        refreshButton.setVisible(true);
        refreshButton.setEnabled(true);
        refreshButton.addListener(new UpdateChatBoardListener());
        //addComponent(refreshButton);
        refreshButton.setParent(getContent());
        //  timer = new Timer(10000, new RefreshBoardListener(refreshButton));
    }

    private void addMessages() {
        if (topic != null) {
            for (Message message : topic.getTopicMessage()) {
                getContent().addComponent(new MessageRow(message));
            }
        }
    }

    public void addMessages(Collection<Message> messages) {
        addedMessages.clear();
        for (Message message : messages) {
            if (message == null) {
                continue;
            }
            MessageRow messageRow = new MessageRow(message);
            addComponent(messageRow);
            addedMessages.add(messageRow);
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
