package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.service.TopicService;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatBoard;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import java.util.Collection;

public class UpdateChatBoardListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent event) {
        VerticalLayout layout = (VerticalLayout)event.getButton().getParent().getParent().getParent().getParent();
        ChatBoard chatBoard = (ChatBoard)(layout).getComponent(1);
        Topic topic = chatBoard.getTopic();
        int size = topic.getTopicMessage().size();
        Message lastMessage = topic.getTopicMessage().get(size - 1);
        Collection<Message> messages = TopicService.loadLastPost(topic.getId(), lastMessage.getId());
        chatBoard.addMessages(messages);
    }
}
