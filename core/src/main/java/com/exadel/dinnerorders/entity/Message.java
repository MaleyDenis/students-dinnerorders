package com.exadel.dinnerorders.entity;

import javax.persistence.*;
import java.security.AllPermission;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity(name = "message")
public class Message {
    @Id
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "text", nullable = true)
    private String text;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "message", cascade = CascadeType.ALL)
    private List<Content> contentList;

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
        for (Content content : this.contentList) {
            content.setMessage(this);
        }
    }

    public Message() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addContent(Content content) {
        if (contentList == null) {
            contentList = new ArrayList<Content>();
        }
        content.setMessage(this);
        contentList.add(content);
    }
}
