package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.listeners.*;
import com.vaadin.ui.*;

public class CreateMessageChatPanel extends Panel {
    private HorizontalLayout horizontalLayout;
    private TextArea inputMessage;
    private Message newMessage;
    private VerticalLayout controlGroup;
    private Window window;

    public CreateMessageChatPanel(Window window){
        super();
        this.window = window;
        setSizeFull();
        initComponent();
        locateComponent();
    }

    public void initLayout(){
        horizontalLayout = new HorizontalLayout();
        setContent(horizontalLayout);
        controlGroup = new VerticalLayout();
        controlGroup.setSizeFull();
    }

    public void initComponent(){
        initLayout();
        inputMessage  = new TextArea();
        inputMessage.setInputPrompt("message");
        inputMessage.setColumns(40);
        initControlGroup();
    }

    private void initControlGroup() {
        Button sendMessage = new Button("Send");
        sendMessage.addListener(new SendMessageListener(controlGroup,
                ((WebApplicationController)window.getApplication()).getApplication().getEventBus()));

        Button refresh = new Button("Refresh");
        refresh.addListener(new UpdateChatBoardListener());

        FileUploadReceiver receiver =
                new FileUploadReceiver(((WebApplicationController)window.getApplication()).getApplication().getUser());

        Upload upload = new Upload(null, receiver);
        upload.setButtonCaption(null);
        upload.addListener(new UploadSuccessListener());
        upload.addListener(new UploadFailedListener());
        upload.addListener(new StartUploadListener());
        controlGroup.addComponent(sendMessage);
        controlGroup.addComponent(refresh);
        controlGroup.addComponent(upload);
    }

    public void locateComponent(){
        horizontalLayout.addComponent(inputMessage);
        horizontalLayout.addComponent(controlGroup);
        horizontalLayout.setComponentAlignment(controlGroup, Alignment.MIDDLE_LEFT);
    }

    public VerticalLayout getControlGroup() {
        return controlGroup;
    }

    public Message getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }
}
