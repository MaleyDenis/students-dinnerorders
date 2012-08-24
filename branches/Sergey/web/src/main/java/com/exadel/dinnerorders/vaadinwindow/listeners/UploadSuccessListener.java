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

import java.io.File;

public class UploadSuccessListener implements Upload.SucceededListener {
    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        event.getComponent().getWindow().showNotification("OK");
        CreateMessageChatPanel createMessageChatPanel =
                (CreateMessageChatPanel)event.getComponent().getParent().getParent().getParent();

        User user = ((WebApplicationController)createMessageChatPanel.getApplication()).getApplication().getUser();
        Content content = new Content();
        content.setUrl(makeFileNameUnique(user, event.getFilename()));
        Message message = new Message();
        message.addContent(content);
        message.setUser(user);
        message.setDate(DateUtils.getCurrentTime());
        createMessageChatPanel.setNewMessage(message);
        ((VerticalLayout)event.getComponent().getParent()).getComponent(0).setEnabled(true);
        ((WebApplicationController)event.getComponent().getApplication())
                .getApplication().getEventBus().post(new UploadFinishedEvent());
    }

    private String makeFileNameUnique(User user, String filename) {
        File file = new File("C:/content/" + user.getLdapLogin() + filename);
        file.renameTo(new File("C:/content/"+user.getLdapLogin() + System.currentTimeMillis() + filename));
        return file.getAbsolutePath();
    }
}
