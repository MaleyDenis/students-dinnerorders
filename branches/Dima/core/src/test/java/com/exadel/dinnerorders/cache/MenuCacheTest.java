package com.exadel.dinnerorders.cache;

import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 1.8.12
 */

public class MenuCacheTest {
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu menu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;

    @Before
    public void setUp () {
        menuDAO = new MenuDAO();
        menuItemDAO = new MenuItemDAO();
        Timestamp date = new Timestamp((new Date()).getTime());
        menuItem1 = new MenuItem(null, Weekday.MONDAY, "rrrrr", new Double(6.7));
        menuItem2 = new MenuItem(null, Weekday.TUESDAY, "ttttt", new Double(77));
        menuItemDAO.create(menuItem1);
        menuItemDAO.create(menuItem2);
        menu = new Menu(null, "Josh", new Timestamp(date.getTime() - 1000), new Timestamp(date.getTime() + 1000), new HashMap<Weekday, List<MenuItem>>());
        menu.addItem(menuItem1);
        menu.addItem(menuItem2);
        menuDAO.create(menu);
        menu.getDateEnd().setNanos(0);
        menu.getDateStart().setNanos(0);
    }

    @Test
    public void testGet () {
        try {
            MenuCache cache = MenuCache.getInstance();
            Menu temp = cache.get(menu.getId());
            Assert.assertEquals(menu, temp);
            menuDAO.delete(menu);
            temp = cache.get(menu.getId());
            Assert.assertEquals(menu, temp);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @After
    public void tearDown () {
        menuItemDAO.delete(menuItem1);
        menuItemDAO.delete(menuItem2);
    }
}
