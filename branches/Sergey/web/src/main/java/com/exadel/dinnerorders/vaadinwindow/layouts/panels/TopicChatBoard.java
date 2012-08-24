package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

import java.sql.Timestamp;
import java.util.Date;

public class TopicChatBoard extends Panel {

    private Table topicBoardTable;

    public TopicChatBoard(){
        super();
        initTable();
        locateComponent();
    }

    private void locateComponent() {
        addComponent(topicBoardTable);
        topicBoardTable.addItem(new Object[]{"topic1","Author1",new Timestamp(new Date().getTime())},1);
        topicBoardTable.addItem(new Object[]{"topic2","Author2",new Timestamp(new Date().getTime())},2);

    }

    public void initTable(){
        topicBoardTable = new Table();
        topicBoardTable.addContainerProperty("User",String.class,null);
        topicBoardTable.addContainerProperty("Topic",String.class,null);
        topicBoardTable.addContainerProperty("Date", Timestamp.class,null);
        topicBoardTable.isSelectable();
        topicBoardTable.setMultiSelect(true);
        topicBoardTable.setImmediate(true);
        topicBoardTable.setSortDisabled(true);
        topicBoardTable.setSizeFull();

    }
}
