package com.exadel.dinnerorders.cache;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Василий Силин
 * Date: 1.8.12
 */
public class MenuItemCacheTest {
    private MenuItemDAO menuItemDAO;
    private MenuItem menuItem;

    @Before
    public void setUp () {
        menuItemDAO = new MenuItemDAO();
        menuItem = new MenuItem(null, Weekday.MONDAY, "rrrrr", new Double(6.7));
        menuItemDAO.create(menuItem);
    }

    @Test
    public void testGet () {
        try {
            MenuItemCache cache = MenuItemCache.getInstance();
            MenuItem temp = cache.get(menuItem.getId());
            Assert.assertEquals(menuItem, temp);
            menuItemDAO.delete(menuItem);
            temp = cache.get(menuItem.getId());
            Assert.assertEquals(menuItem, temp);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }
}
