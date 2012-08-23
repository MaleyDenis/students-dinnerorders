package com.exadel.dinnerorders.entity;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity(name = "content")
public class Content {
    @Id
    @Column(name = "content_id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(targetEntity = Message.class)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    public Content() {
        super();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
