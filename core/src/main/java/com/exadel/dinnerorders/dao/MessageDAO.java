package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import com.exadel.dinnerorders.entity.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
public class MessageDAO extends BaseDAO<Message> {
    @Override
    public boolean create(Message newItem) {
        SessionFactory sessionFactory = getSessionFactory(this);
        Session session  = sessionFactory.openSession();
        session.beginTransaction();
        session.save(newItem);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Message item) throws IllegalAccessException, InstantiationException {
        return false;
    }

    @Override
    public boolean delete(Message item) throws IllegalAccessException, InstantiationException {
        return false;
    }

    @Override
    public Message load(Long value) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public Collection<Message> loadAll() throws IllegalAccessException, InstantiationException {
        return null;
    }
}
