package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.listeners.*;
import com.vaadin.ui.*;

public class CreateMessageChatPanel extends Panel {
    private GridLayout gridLayout;
    private TextArea inputMessage;
    private Message newMessage;
    private VerticalLayout controlGroup;
    private Window window;

    public CreateMessageChatPanel(Window window){
        super();
        this.window = window;
        initComponent();
        locateComponent();
        setSizeFull();
    }

    public void initLayout(){
        gridLayout = new GridLayout(2, 1);
        gridLayout.setColumnExpandRatio(0, 7f);
        gridLayout.setColumnExpandRatio(1, 3f);
        setContent(gridLayout);
        gridLayout.setSizeFull();
        controlGroup = new VerticalLayout();
        controlGroup.setSizeFull();
    }

    public void initComponent(){
        initLayout();
        inputMessage  = new TextArea();
        inputMessage.setInputPrompt("message");
        inputMessage.setColumns(40);
        inputMessage.setWidth(95, UNITS_PERCENTAGE);
        inputMessage.setImmediate(true);
        inputMessage.addListener(new KeyTypeListener());
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
        gridLayout.addComponent(inputMessage);
        gridLayout.addComponent(controlGroup);
        gridLayout.setComponentAlignment(controlGroup, Alignment.MIDDLE_LEFT);
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
