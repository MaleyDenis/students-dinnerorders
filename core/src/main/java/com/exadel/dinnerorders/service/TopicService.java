package com.exadel.dinnerorders.service;


import com.exadel.dinnerorders.dao.TopicDAO;
import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Topic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TopicService {

    private static TopicDAO topicDAO;

    public static boolean saveTopic(Topic topic){
        List<Message> messages = topic.getTopicMessage();
        for(Message message : messages){
            if(!message.getText().isEmpty()){
                topicDAO.create(topic);
                return true;
            }
        }
        return false;
    }

    public static Collection<Message> loadLastPost(Long topic_id, Long message_id){
        TopicDAO topicDAO1 = new TopicDAO();
        Topic topic = topicDAO1.load(topic_id);
        Collection<Message>messages = new ArrayList<Message>();
        for (Message message : topic.getTopicMessage()){
              if(message_id < message.getId()){
                  messages.add(message);
              }
        }
        return messages;
    }


    public static Collection<Topic> loadAll(){
        TopicDAO topicDAO1 = new TopicDAO();
        return topicDAO1.loadAll();
    }

    public static int posts(Topic topic){
        return topic.getTopicMessage().size();

    }

    public static Topic loadByName(String topicName) {
        TopicDAO topicDAO1 = new TopicDAO();
        Collection<Topic> topics = topicDAO1.loadAll();
        Topic topic = null;
        for ( Topic loaded : topics ) {
            if (loaded.getName().equals(topicName)) {
                return loaded;
            }
        }
        return topic;
    }
}
