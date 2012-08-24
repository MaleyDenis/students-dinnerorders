package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.service.TopicService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.ShowTopicPostButtonEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatBoard;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatPanel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;


public class ShowTopicPostButtonListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Application application = ((WebApplicationController)clickEvent.getButton().getApplication()).getApplication();
        application.getEventBus().post(new ShowTopicPostButtonEvent());
        ChatPanel chatPanel = (ChatPanel)clickEvent.getButton().getParent().getParent().getParent().getParent().getParent();
        Component topicChatBoard = ((VerticalLayout)clickEvent.getButton().getParent().getParent().getParent().getParent()).getComponent(1);
       // CreateTopicPanel createTopicPanel = new CreateTopicPanel(clickEvent.getButton().getApplication().getMainWindow());

        ChatBoard chatBoard = new ChatBoard();
        String topicName = clickEvent.getButton().getCaption();
        Topic topic = TopicService.loadByName(topicName);
        chatBoard.setTopic(topic);
        chatPanel.exchangeTopicsListAndChatBoard(topicChatBoard, chatBoard);
    }
}
