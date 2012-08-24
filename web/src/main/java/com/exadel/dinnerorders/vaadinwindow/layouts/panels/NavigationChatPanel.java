package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.vaadin.ui.*;

public class NavigationChatPanel extends Panel {
    private HorizontalLayout horizontalLayout;
    private Button createTopicButton;
    private Button favoriteTopicButton;
    private Button addFavoriteButton;
    private ComboBox topicComboBox;

    public NavigationChatPanel(){
        super();
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
        initButton();
        topicComboBox = new ComboBox();


    }

    public void initButton(){
        createTopicButton = new Button("Crate topic");
        favoriteTopicButton = new Button("Favorite topic");
        addFavoriteButton = new Button("add topic");
    }

    public void locateComponent(){
        horizontalLayout.addComponent(createTopicButton);
        horizontalLayout.setComponentAlignment(createTopicButton, Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(favoriteTopicButton);
        horizontalLayout.addComponent(addFavoriteButton);
        horizontalLayout.addComponent(topicComboBox);
    }



}
