package com.exadel.dinnerorders.vaadinwindow.events;


import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.CreateTopicPanel;
import com.vaadin.ui.VerticalLayout;

public class SaveTopicButtonEvent {
    private final VerticalLayout parent;    private ChatPanel chatPanel;

    public CreateTopicPanel getCreateTopicPanel() {
        return createTopicPanel;
    }

    public ChatPanel getChatPanel() {

        return chatPanel;
    }

    private CreateTopicPanel createTopicPanel;

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public void setCreateTopicPanel(CreateTopicPanel createTopicPanel) {
        this.createTopicPanel = createTopicPanel;
    }

    public SaveTopicButtonEvent(VerticalLayout parent) {
        this.parent = parent;
    }

    public VerticalLayout getParent() {
        return parent;
    }
}
