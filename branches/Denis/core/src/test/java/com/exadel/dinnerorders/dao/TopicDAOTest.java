package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Topic;
import org.junit.Test;

import java.util.Collection;

public class TopicDAOTest {
    @Test
    public void testCreate() throws Exception {


    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testLoad() throws Exception {

    }

    @Test
    public void testLoadAll() throws Exception {
        TopicDAO topicDAO = new TopicDAO();
        Collection<Topic> topics = topicDAO.loadAll();
        //System.out.println(topics.iterator().next().getName());
        for (Topic topic : topics){
            System.out.println(topic.getName());
            /*Collection<Message> messages = topic.getTopicMessage();
            for(Message message1 : messages){
                System.out.println(message1.getText());
            }
            System.out.println("-----------");  */
        }

    }
}
