package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultMysqlConnectionProvider;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.WeekdayComparator;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */

@DbConnection(connectionType = DefaultMysqlConnectionProvider.class)
public class MenuItemDAO extends BaseDAO<MenuItem> {
    private Logger logger = Logger.getLogger(MenuItemDAO.class);

    public boolean create(MenuItem newItem)  {
        Session session = openSession();
        try {
            if (session != null) {
                newItem.setId(getID());
                session.beginTransaction();
                session.save(newItem);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public boolean update(MenuItem item)  {
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                session.update(item);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public boolean delete(MenuItem item) {
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                session.delete(item);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return false;
    }

    public MenuItem load(Long id)  {
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                MenuItem menuItem = (MenuItem) session.get(MenuItem.class, id);
                session.getTransaction().commit();
                return menuItem;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeSession();
        }
        return null;
    }

    public Collection<MenuItem> loadAll()   {
        List<MenuItem> items = new ArrayList<MenuItem>();
        Session session = openSession();
        try {
            if (session != null) {
                session.beginTransaction();
                Query query = session.createQuery("from MenuItem");//createQuery("select item from MenuItem item order by item.weekday");
                items = query.list();
                Collections.sort(items, new WeekdayComparator());
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
