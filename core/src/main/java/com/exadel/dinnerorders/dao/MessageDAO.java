package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Content;
import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import com.exadel.dinnerorders.entity.Message;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
public class MessageDAO extends BaseDAO<Message> {
    private static final Logger logger = Logger.getLogger(MessageDAO.class);
    @Override
    public boolean create(Message newItem)  {
        Session session = null;
        try {
            for (Content content : newItem.getContentList()) {
                content.setId(getID());
            }
            SessionFactory factory = getSessionFactory(this);
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            newItem.setId(getID());
            session.save(newItem);
            transaction.commit();
        } catch (Exception e) {
            logger.error("MessageDAO: error while creating message", e);
            return false;
        } finally {
            if (session != null) {
            session.flush();
            session.close();
            }
        }
        return true;
    }

    @Override
    public boolean update(Message item) {
        Session session = null;
        try {
            SessionFactory factory = getSessionFactory(this);
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            logger.error("MessageDAO: error while updating message", e);
            return false;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean delete(Message item) {
        Session session = null;
        try {
            SessionFactory factory = getSessionFactory(this);
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        } catch (Exception e) {
            logger.error("MessageDAO: error while deleting message", e);
            return false;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return true;
    }

    @Override
    public Message load(Long value) {
        Message message;
        Session session = null;
        try {
            SessionFactory factory = getSessionFactory(this);
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            message = (Message)session.get(Message.class, value);
            transaction.commit();
        } catch (Exception e) {
            logger.error("MessageDAO: error while loading message", e);
            message = null;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return message;
    }

    @Override
    public Collection<Message> loadAll() {
        List loaded;
        Collection <Message> messages = null;
        Session session = null;
        try {
            SessionFactory factory = getSessionFactory(this);
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("select * from message");
            loaded = query.list();

            if (loaded != null) {
                messages = readContent(loaded, session);
            }
            //messages = session.createCriteria(Message.class).list();
            transaction.commit();
        } catch (Exception e) {
            logger.error("MessageDAO: error while loadAll messages", e);
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return messages;
    }

    private Collection<Message> readContent(List loaded, Session session) {
        List<Message> messages = new ArrayList<Message>();
        for (Object object : loaded) {
            Message message = (Message)session.get(Message.class, ((BigInteger)((Object[])object)[0]).longValue());
            messages.add(message);
        }
        return messages;
    }
}
