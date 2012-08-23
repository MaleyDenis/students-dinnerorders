package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.vaadinwindow.layouts.MessageRow;
import com.vaadin.ui.Panel;

public class ChatBoard extends Panel {
    private Topic topic;
    public ChatBoard() {
        super();
        addMessages();
    }

    private void addMessages() {
        if (topic != null) {
            for (Message message : topic.getTopicMessage()){
                getContent().addComponent(new MessageRow(message));
            }
        }
    }
}
