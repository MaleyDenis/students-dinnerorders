package com.exadel.dinnerorders.vaadinwindow.layouts;

import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.service.LdapService;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

import java.sql.Timestamp;

public class MessageRow extends GridLayout {
    public static final int DEFAULT_COLUMNS = 3;
    public static final int DEFAULT_ROWS = 2;
    public static final String PHOTO_EXTENSION = ".jpeg";
    public static final int DEFAULT_MESSAGE_ROW_LENGTH = 30;
    private Embedded photo;
    private Message message;
    private Label dateLabel;
    private VerticalLayout content;
    public MessageRow(Message message) {
        super(DEFAULT_COLUMNS, DEFAULT_ROWS);
        this.message = message;
        setConstraints();
        initComponents();
        locateComponents();
        setSizeFull();
    }

    private void initComponents() {
        initPhoto();
        initDateLabel();
        initMessageContent();
    }

    private void initMessageContent() {
        content = new VerticalLayout();
        Label messageText = new Label(message.getText(), Label.CONTENT_RAW);
        messageText.setStyleName("chatmessage");
        messageText.setWidth(DEFAULT_MESSAGE_ROW_LENGTH, UNITS_EM);
        content.addComponent(messageText);
        for (int i = 0; i < message.getContentList().size(); i++) {
            Embedded picture = new Embedded(null, new ExternalResource(message.getContentList().get(i).getUrl()));
            PopupView popupView = new PopupView(createContent(picture));
            popupView.setHideOnMouseOut(true);
            popupView.setSizeFull();
            content.addComponent(popupView);
        }
        content.setStyleName("messagerow");
        content.setSizeFull();
    }

    private PopupView.Content createContent(final Embedded picture) {
        final String url = ((ExternalResource)picture.getSource()).getURL();
        final int width = 100;
        return new PopupView.Content() {
            @Override
            public String getMinimizedValueAsHTML() {
                return "<img src=\"" + url + "\" width=\"" + width + "px\"/>";
            }

            @Override
            public Component getPopupComponent() {
                return picture;
            }
        };
    }

    private void initDateLabel() {
        Timestamp date = message.getDate();
        StringBuilder timeString = new StringBuilder(date.toString());
        String dateString = timeString.substring(0, MenuCreationPanel.DATE_PATTERN.length());
        timeString.delete(0, MenuCreationPanel.DATE_PATTERN.length());
        timeString.delete(timeString.length() - 2, timeString.length());
        dateLabel = new Label(dateString + "<br>" +timeString.toString(), Label.CONTENT_RAW);
        dateLabel.setStyleName("chatdate");
        dateLabel.setWidth(MenuCreationPanel.DATE_PATTERN.length(), UNITS_EM);
    }

    private void initPhoto() {
        LdapService ldapService = new LdapService();
        String userName = message.getUser().getUserName();
        String userPhoto = ldapService.loadUserPhotoURI(userName) + PHOTO_EXTENSION;
        if (userPhoto.equals("Empty" + PHOTO_EXTENSION)) {
            userPhoto = "/VAADIN/themes/runo/icons/64/user.png";
        }
        int indexOfSpace = userName.indexOf(' ');
        photo = new Embedded(userName.substring(indexOfSpace), new ExternalResource(userPhoto));
        photo.setWidth(40, UNITS_PIXELS);
        photo.setStyleName("chatphoto");
    }

    private void setConstraints() {
        setColumnExpandRatio(0, 3f);
        setColumnExpandRatio(1, 15f);
        setColumnExpandRatio(2, 0.5f);
    }

    private void locateComponents() {
        addComponent(photo);
        addComponent(content);
        addComponent(dateLabel);
        addComponent(new Label("<br>", Label.CONTENT_RAW));
    }
}
