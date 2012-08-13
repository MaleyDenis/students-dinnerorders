package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
public class MenuDAO extends BaseDAO<Menu> {
    private Logger logger = Logger.getLogger(MenuDAO.class);

    public boolean create(Menu newMenu) {
        Session session = openSession();
        try{
            if(session != null) {
                newMenu.setId(getID());
                session.beginTransaction();
                session.save(newMenu);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public boolean update(Menu menu) {
        Session session = openSession();
        try{
            if(session != null) {
                session.beginTransaction();
                session.update(menu);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public boolean delete(Menu menu) {
        Session session = openSession();
        try{
            if(session != null) {
                session.beginTransaction();
                session.delete(menu);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public Menu load(Long id) {
        Session session = openSession();
        try{
            if(session != null) {
                Menu menu = null;
                session.beginTransaction();
                menu = (Menu) session.get(Menu.class, id);
                session.getTransaction().commit();
                return menu;
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            closeSession();
        }
        return null;
    }

    public Collection<Menu> loadAll(){
        List<Menu> items = new ArrayList<Menu>();
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                Query query = session.createQuery("select menu from Menu menu order by menu.id");
                items = query.list();
                session.getTransaction().commit();
                if (items != null) {
                    return items;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return Collections.emptyList();
    }

    public Collection<Long> getMenuIds() {
        List<Long> items = new ArrayList<Long>();
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                Query query = session.createQuery("select menu.id from Menu menu order by menu.id");
                items = query.list();
                session.getTransaction().commit();
                if (items != null) {
                    return items;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return Collections.emptyList();
    }
}