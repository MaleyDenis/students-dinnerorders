package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */
public class MenuItemDAOTest extends TestCase {
    @Test
    public void test() throws Exception {
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        MenuItem menuItem1 = new MenuItem((long)1, Weekday.MONDAY, "1", (double)1);
        MenuItem menuItem2 = new MenuItem((long)2, Weekday.TUESDAY, "2", (double)2);
        MenuItem menuItem3 = new MenuItem((long)3, Weekday.WEDNESDAY, "3", (double)3);
        Assert.assertTrue(menuItemDAO.create(menuItem1));
        Assert.assertTrue(menuItemDAO.create(menuItem2));
        Assert.assertTrue(menuItemDAO.create(menuItem3));
        Assert.assertTrue(menuItemDAO.update(new MenuItem((long)1, Weekday.MONDAY, "1", (double)4)));
        Assert.assertTrue(menuItemDAO.update(new MenuItem((long)2, Weekday.TUESDAY, "2", (double)5)));
        Assert.assertTrue(menuItemDAO.update(new MenuItem((long)3, Weekday.WEDNESDAY, "3", (double)6)));
        Assert.assertTrue(menuItemDAO.delete(menuItem1));
        Assert.assertTrue(menuItemDAO.delete(menuItem2));
        Assert.assertTrue(menuItemDAO.delete(menuItem3));
    }
}
