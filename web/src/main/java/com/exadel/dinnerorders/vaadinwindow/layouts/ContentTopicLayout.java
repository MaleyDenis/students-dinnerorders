package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.vaadinwindow.listeners.ViewTopicButtonListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

import java.sql.Timestamp;

public class ContentTopicLayout extends HorizontalLayout{
    private Button viewTopicButton;
    private Label dateCreationLabel;
    private Label userCreationLabel;
    private Label viewsLabel;
    private String themeTopic;
    private Timestamp dateCreationTopic;
    private String userCreationTopic;
    private int views;
    private Table table;

    public ContentTopicLayout(String themeTopic, Timestamp dateCreationTopic, String userCreationTopic, int views){
        super();
        this.themeTopic = themeTopic;
        this.dateCreationTopic = dateCreationTopic;
        this.userCreationTopic = userCreationTopic;
        this.views = views;
        initComponent();
        locateComponent();
        setSizeFull();
    }

    private void initComponent(){
        viewTopicButton = new Button(themeTopic);
        viewTopicButton.setStyleName(BaseTheme.BUTTON_LINK);
        viewTopicButton.addListener(new ViewTopicButtonListener());
        dateCreationLabel = new Label(dateCreationTopic.toString());
        userCreationLabel = new Label(userCreationTopic);
        viewsLabel = new Label(String.valueOf(views));

    }

    private void locateComponent(){
        addComponent(viewTopicButton);
        setComponentAlignment(viewTopicButton, Alignment.MIDDLE_LEFT);
        addComponent(userCreationLabel);
        addComponent(viewsLabel);
        addComponent(dateCreationLabel);

    }

}
