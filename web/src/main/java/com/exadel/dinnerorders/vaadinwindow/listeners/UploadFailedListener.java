package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Content;
import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.UploadFinishedEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.CreateMessageChatPanel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;

public class UploadFailedListener implements Upload.FailedListener {
    @Override
    public void uploadFailed(Upload.FailedEvent event) {
        event.getComponent().getWindow().showNotification("FAIL");
        CreateMessageChatPanel createMessageChatPanel =
                (CreateMessageChatPanel)event.getComponent().getParent().getParent().getParent();
        User user = ((WebApplicationController)createMessageChatPanel.getApplication()).getApplication().getUser();
        Message message = new Message();
        message.setContentList(new ArrayList<Content>());
        message.setUser(user);
        message.setDate(DateUtils.getCurrentTime());
        createMessageChatPanel.setNewMessage(message);
        ((VerticalLayout)event.getComponent().getParent()).getComponent(0).setEnabled(true);

        ((WebApplicationController)event.getComponent().getApplication())
                .getApplication().getEventBus().post(new UploadFinishedEvent());
    }
}
