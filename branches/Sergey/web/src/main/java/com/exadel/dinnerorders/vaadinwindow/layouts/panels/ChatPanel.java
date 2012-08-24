package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

public class ChatPanel extends Panel {
    private TopicChatBoard topicChatBoard;
    private NavigationChatPanel navigationChatPanel;
    private CreateMessageChatPanel createMessageChatPanel;
    private Window window;

    public ChatPanel(Window window) {
        super();
        this.window = window;
        initComponents();
        locateComponents();
        setStyleName("chatpanel");
    }

    private void initComponents() {
        navigationChatPanel = new NavigationChatPanel();
        topicChatBoard  = new TopicChatBoard();
        createMessageChatPanel = new CreateMessageChatPanel(window);
        CreateTopicPanel createTopicPanel = new CreateTopicPanel(window);
    }

    private void locateComponents() {
        addComponent(navigationChatPanel);
        addComponent(topicChatBoard);
        addComponent(createMessageChatPanel);
        createMessageChatPanel.setVisible(false);
    }

    public void exchangeTopicsListAndChatBoard(Component old, Component newComp) {
        replaceComponent(old, newComp);
        createMessageChatPanel.setVisible(true);
    }

    public void hideMessageCreate() {
        createMessageChatPanel.setVisible(false);
    }
}

