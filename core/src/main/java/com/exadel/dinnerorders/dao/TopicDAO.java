package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;

@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
public class TopicDAO extends BaseDAO<Topic>{
    private Logger logger = Logger.getLogger(UserDAO.class);
    @Override
    public boolean create(Topic newItem) {
        SessionFactory sessionFactory = getSessionFactory(this);
        Session session = null;
        try {
            session  = sessionFactory.openSession();
            session.beginTransaction();
            newItem.setId(getID());
            for(Message message : newItem.getTopicMessage()){
                message.setId(getID());
                for (Content content: message.getContentList()){
                    content.setId(getID());
                }
            }
            session.save(newItem);
            session.getTransaction().commit();
            session.flush();
            return true;
        } catch (Exception e){
            logger.error("Create error the method",e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean update(Topic item)  {
        SessionFactory sessionFactory = getSessionFactory(this);
        Session session = null;
        try {
            session = sessionFactory.openSession();
        session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            session.flush();
            return true;
        } catch (Exception e){
            logger.error("Create error the method",e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Topic item)  {
        SessionFactory sessionFactory = getSessionFactory(this);
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(item);
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e){
            logger.error("Create error the method",e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    @Override
    public Topic load(Long value) {
        SessionFactory sessionFactory = getSessionFactory(this);
        Session session = null;
        Topic topic = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            topic = (Topic)session.get(Topic.class, value);
            for (Message message : topic.getTopicMessage()){
               message.getContentList();
            }
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e){
            logger.error("Load error the method",e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return topic;
    }

    @Override
    public Collection<Topic> loadAll() {
        SessionFactory sessionFactory = getSessionFactory(this);
        Session session = null;
        List<Topic> topics = null;
        try {
            session = sessionFactory.openSession();
            topics = session.createCriteria(Topic.class).list();
            for (Topic topic : topics){
                for(Message message : topic.getTopicMessage()){
                    message.getContentList();
                }
            }
        } catch (Exception e){
            logger.error("loadAll error the method",e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return topics;
    }
}
