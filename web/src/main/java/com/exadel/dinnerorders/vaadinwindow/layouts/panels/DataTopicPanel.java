package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.dao.TopicDAO;
import com.exadel.dinnerorders.entity.Content;
import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.UserService;
import com.exadel.dinnerorders.vaadinwindow.application.WebApplicationController;
import com.exadel.dinnerorders.vaadinwindow.events.SaveTopicButtonEvent;
import com.exadel.dinnerorders.vaadinwindow.listeners.SaveTopicButtonListener;
import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataTopicPanel extends VerticalLayout{
    private Label dateCreationTopic;
    private Label nameUser;
    private Label themeTopic;
    private Label message;
    private TextField dateCreationField;
    private TextField nameUserField;
    private TextField themeTopicField;
    private TextArea messageArea;
    private Button saveTopicButton;
    private Window window;

    public DataTopicPanel(Window window){
        super();
        this.window = window;
        initComponent();
        locateComponent();
        ((WebApplicationController)window.getApplication()).getApplication().getEventBus().register(this);
    }

    private void locateComponent() {
        addComponent(dateCreationTopic);
        addComponent(dateCreationField);
        dateCreationField.setColumns(20);
        addComponent(nameUser);
        addComponent(nameUserField);
        nameUserField.setColumns(20);
        addComponent(themeTopic);
        addComponent(themeTopicField);
        themeTopicField.setSizeFull();
        themeTopicField.setRequired(true);
        addComponent(message);
        addComponent(messageArea);
        messageArea.setSizeFull();
        addComponent(saveTopicButton);
    }

    private void initLabel(){
        saveTopicButton = new Button("Create topic");
        saveTopicButton.addListener(new SaveTopicButtonListener());
        saveTopicButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        dateCreationTopic = new Label("Date");
        nameUser = new Label("User");
        themeTopic = new Label("Theme topic");
        message = new Label("Message");
        themeTopicField = new TextField();
        messageArea = new TextArea();
        dateCreationField = new TextField();
        dateCreationField.setValue(new Date().toString());
        nameUserField = new TextField();
        nameUserField.setValue(((WebApplicationController)window.getApplication())
                .getApplication().getUser().getUserName());
    }

    private void initComponent() {
        initLabel();

    }
    private Topic getTopic(){
        TopicDAO topicDAO = new TopicDAO();
        Topic topic = new Topic();
        User user = ((WebApplicationController)window.getApplication()).getApplication().getUser();
        Message message = new Message();
        List<Message> messages = new ArrayList<Message>();
        List<Content> contents = new ArrayList<Content>();
        Content content = new Content();
        content.setId(null);
        content.setUrl("hfhfhhgh");
        contents.add(content);
        message.setId(null);
        message.setText(messageArea.getValue().toString());
        message.setTopic(topic);
        message.setDate(new Timestamp(new Date().getTime()));
        message.setUser(UserService.findUserByUserName(user.getUserName()));
        message.setContentList(contents);
        messages.add(message);
        topic.setName(themeTopicField.getValue().toString());
        topic.setDateCreation(new Timestamp(new Date().getTime()));
        topic.setId(null);
        topic.setUser(UserService.findUserByUserName(user.getUserName()));
        topic.setTopicMessage(messages);
        topicDAO.create(topic);
        return topic;
    }

    @Subscribe
    public void saveTopic(SaveTopicButtonEvent saveTopicButtonEvent) {
        VerticalLayout eventParent = saveTopicButtonEvent.getParent();
        CreateTopicPanel createTopicPanel = saveTopicButtonEvent.getCreateTopicPanel();
        ChatPanel chatPanel = saveTopicButtonEvent.getChatPanel();

        if (eventParent != this) {
            return;
        }
        ChatBoard board = new ChatBoard();
        board.setTopic(getTopic());
        chatPanel.exchangeTopicsListAndChatBoard(createTopicPanel, board);
    }
}
