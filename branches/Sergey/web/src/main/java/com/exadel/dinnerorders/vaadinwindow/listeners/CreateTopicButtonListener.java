package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.CreateTopicButtonEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.CreateTopicPanel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class CreateTopicButtonListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Application application = ((WebApplicationController)clickEvent.getButton().getApplication()).getApplication();
        application.getEventBus().post(new CreateTopicButtonEvent());
        ChatPanel chatPanel = (ChatPanel)clickEvent.getButton().getParent().getParent().getParent().getParent().getParent();
        Component topicChatBoard = ((VerticalLayout)clickEvent.getButton().getParent().getParent().getParent().getParent()).getComponent(1);
        CreateTopicPanel createTopicPanel = new CreateTopicPanel(clickEvent.getButton().getApplication().getMainWindow());
        chatPanel.replaceComponent(topicChatBoard,createTopicPanel);
    }
}
