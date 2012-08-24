package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.SaveTopicButtonEvent;
import com.exadel.dinnerorders.vaadinwindow.events.ShowTopicPostButtonEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.ChatPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.CreateTopicPanel;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class SaveTopicButtonListener implements Button.ClickListener{
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        Application application = ((WebApplicationController)clickEvent.getButton().getApplication()).getApplication();
        VerticalLayout mainPanel = (VerticalLayout)clickEvent.getButton().getParent();

        SaveTopicButtonEvent event = new SaveTopicButtonEvent(mainPanel);
        ChatPanel chatPanel = (ChatPanel)clickEvent.getButton().getParent().getParent().getParent().getParent().getParent().getParent();
        CreateTopicPanel createTopicPanel = ((CreateTopicPanel)clickEvent.getButton().getParent().getParent().getParent().getParent());
        event.setChatPanel(chatPanel);
        event.setCreateTopicPanel(createTopicPanel);
        application.getEventBus().post(event);

        application.getEventBus().post(new ShowTopicPostButtonEvent());
}
}
