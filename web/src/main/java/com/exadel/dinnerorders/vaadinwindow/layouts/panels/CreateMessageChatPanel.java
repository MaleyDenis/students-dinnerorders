package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.*;

public class CreateMessageChatPanel extends Panel {
    private HorizontalLayout horizontalLayout;
    private TextArea inputMessage;
    private Button sendMessage;

    public CreateMessageChatPanel(){
        super();
        setSizeFull();
        initComponent();
        locateComponent();
    }

    public void initLayout(){
        horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        addComponent(horizontalLayout);
    }

    public void initComponent(){
        initLayout();
        inputMessage  = new TextArea();
        inputMessage.setInputPrompt("message");
        inputMessage.setColumns(40);
        sendMessage = new Button("send");

    }

    public void locateComponent(){
        horizontalLayout.addComponent(inputMessage);
        //horizontalLayout.setComponentAlignment(inputMessage, Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(sendMessage);
        //horizontalLayout.setComponentAlignment(inputMessage, Alignment.MIDDLE_RIGHT);
    }

}
