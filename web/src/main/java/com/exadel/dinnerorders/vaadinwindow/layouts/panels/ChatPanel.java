package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

public class ChatPanel extends Panel {
    private ChatBoard chatBoard;
    private TopicChatBoard topicChatBoard;
    private NavigationChatPanel navigationChatPanel;
    private CreateMessageChatPanel createMessageChatPanel;
    private final Window window;
    public ChatPanel(Window window) {
        super();
        this.window = window;
        initComponents();
        locateComponents();
        setStyleName("chatpanel");
        setHeight(750, UNITS_PIXELS);
    }

    private void initComponents() {
        chatBoard = new ChatBoard();
        navigationChatPanel = new NavigationChatPanel();
        topicChatBoard  = new TopicChatBoard();
        createMessageChatPanel = new CreateMessageChatPanel(window);
        createMessageChatPanel.setVisible(false);
        //TODO: init bar, topics list, chat field, chat board
    }

    private void locateComponents() {
        //TODO: Locate bar, topics list, chat field, chat board
        addComponent(navigationChatPanel);
        addComponent(topicChatBoard);
        addComponent(createMessageChatPanel);
        exchangeTopicsListAndChatBoard();
    }

    public void exchangeTopicsListAndChatBoard() {
        createMessageChatPanel.setVisible(true);
        replaceComponent(topicChatBoard, chatBoard);
    }
}

