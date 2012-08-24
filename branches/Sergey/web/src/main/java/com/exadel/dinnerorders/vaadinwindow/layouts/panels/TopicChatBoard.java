package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.entity.Topic;
import com.exadel.dinnerorders.service.TopicService;
import com.exadel.dinnerorders.vaadinwindow.layouts.ContentTopicLayout;
import com.exadel.dinnerorders.vaadinwindow.listeners.ShowTopicPostButtonListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

import java.sql.Timestamp;
import java.util.Collection;

public class TopicChatBoard extends Panel {
    private ContentTopicLayout contentTopicLayout;
    private HorizontalLayout horizontalLayout;
    private Table table;
    private Label label;

    public TopicChatBoard(){
        super();
//        initComponent();
//        locateComponent();
        initTable();
        locateTable();
    }

    private void locateComponent() {
        Collection<Topic> topics = TopicService.loadAll();
        for (Topic topic : topics){
            contentTopicLayout = new ContentTopicLayout(topic.getName(),topic.getDateCreation(),topic.getUser().getUserName(),45);
            addComponent(contentTopicLayout);
        }
    }

    private void initTable(){
        table = new Table();
        table.addContainerProperty("Theme",Button.class,"Theme topic");
        table.setColumnHeaderMode(50);
        table.addContainerProperty("Started By",String.class,"Author topic");
        table.addContainerProperty("Posts",Integer.class,"Number posts in a topic ");
        table.addContainerProperty("Created on",Timestamp.class,"Creation date topic");
        table.setSizeFull();
    }

    private void locateTable(){
        Collection<Topic> topics = TopicService.loadAll();
        int i = 0;
        for (Topic topic : topics){
            table.addItem(new Object[]{getButton(topic.getName()),topic.getUser().getUserName(),
           TopicService.posts(topic),topic.getDateCreation() },i++);
            table.setPageLength(20);
            table.setSelectable(true);

        }
        addComponent(table);

    }

    private Button getButton(String value){
        Label label = new Label(value);
        label.setWidth(40,UNITS_EM);
        Button button = new Button(value);
        button.setStyleName(BaseTheme.BUTTON_LINK);
        button.addListener(new ShowTopicPostButtonListener());
        return button;
    }

    private void initComponent(){
        horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Label("Theme"));
        horizontalLayout.addComponent(new Label("Started By"));
        horizontalLayout.addComponent(new Label("Posts"));
        horizontalLayout.addComponent(new Label("Date created"));
        horizontalLayout.setSizeFull();
        addComponent(horizontalLayout);
    }


}
