package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
public class UserDAO extends BaseDAO<User> {

    private Logger logger = Logger.getLogger(UserDAO.class);


    /**
     * Create new user.
     * New ID will be generated and assigned into current object.
     * Role by default: USER.
     *
     * @param newItem user data for user creation.
     * @return true | false.
     */
    public boolean create(User newItem) {
        Session session = openSession(this);
        try{
            if(session != null) {
                newItem.setId(getID());
                session.beginTransaction();
                session.save(newItem);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession(session);
        }
        return false;
    }


    public boolean update(User item) {
        Session session = openSession(this);
        try{
            if(session != null) {
                session.beginTransaction();
                session.update(item);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession(session);
        }
        return false;
    }


    public boolean delete(User item) {
        Session session = openSession(this);
        try{
            if(session != null) {
                session.beginTransaction();
                session.delete(item);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession(session);
        }
        return false;
    }

    public User load(Long id) {
        Session session = openSession(this);
        try{
            if(session != null) {
                User user = null;
                session.beginTransaction();
                user = (User) session.get(User.class, id);
                session.getTransaction().commit();
                return user;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession(session);
        }
        return null;
    }

    public Collection<User> loadAll() {
        List<User> users = new ArrayList<User>();
        Session session = openSession(this);
        try {
            if (session != null) {
                session.beginTransaction();
                Query query = session.createQuery("select user from User user order by user.id");
                users = query.list();
                session.getTransaction().commit();
                if (users != null) {
                    return users;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession(session);
        }
        return Collections.emptyList();
    }

}
