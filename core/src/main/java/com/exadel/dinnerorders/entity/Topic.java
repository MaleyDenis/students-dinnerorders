package com.exadel.dinnerorders.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/*@Table(name = "topic")
@Entity*/
@javax.persistence.Entity(name = "topic")
public class Topic {
    @Id
    @Column(name = "topic_id",nullable = false)
    private Long id;

    @Column(name = "name_topic")
    private String name;

    @Column(name = "date_creation")
    private Timestamp dateCreation;

    @Column(name = "user_id")
    private Long userID;

    @OneToMany(mappedBy = "topic",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> topicMessage;

    public Topic(Long id, String name, Timestamp dateCreation, Long userID){
        this.id = id;
        this.name = name;
        this.dateCreation = dateCreation;
        this.userID = userID;
    }

    public Topic(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public List<Message> getTopicMessage() {
        return topicMessage;
    }

    public void setTopicMessage(List<Message> topicMessage) {
        this.topicMessage = topicMessage;
        for (Message message : this.topicMessage){
            message.setTopic(this);
        }
    }
    public void addMessage(Message message){
        message.setTopic(this);
        topicMessage.add(message);
    }
}
