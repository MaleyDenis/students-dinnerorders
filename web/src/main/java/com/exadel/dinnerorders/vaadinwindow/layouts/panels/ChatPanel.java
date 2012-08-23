package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.Panel;

public class ChatPanel extends Panel {
    private ChatBoard chatBoard;
    private TopicChatBoard topicChatBoard;
    private NavigationChatPanel navigationChatPanel;
    private CreateMessageChatPanel createMessageChatPanel;
    public ChatPanel() {
        super();
        initComponents();
        locateComponents();
        setStyleName("chatpanel");
    }

    private void initComponents() {
        chatBoard = new ChatBoard();
        navigationChatPanel = new NavigationChatPanel();
        topicChatBoard  = new TopicChatBoard();
        createMessageChatPanel = new CreateMessageChatPanel();
        //TODO: init bar, topics list, chat field, chat board
    }

    private void locateComponents() {
        //TODO: Locate bar, topics list, chat field, chat board
        addComponent(navigationChatPanel);
        addComponent(topicChatBoard);
        addComponent(createMessageChatPanel);
    }

    public void exchangeTopicsListAndChatBoard() {
    }
}

