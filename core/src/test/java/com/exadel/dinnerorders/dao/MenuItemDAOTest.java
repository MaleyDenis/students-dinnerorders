package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */

public class MenuItemDAOTest extends TestCase {
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;
    private MenuItemDAO menuItemDAO;

    @Before
    protected void setUp(){
        menuItem1 = new MenuItem((long)7, Weekday.MONDAY, "4", (double)1);
        menuItem2 = new MenuItem((long)5, Weekday.TUESDAY, "5", (double)2);
        menuItem3 = new MenuItem((long)6, Weekday.WEDNESDAY, "6", (double)3);
        menuItemDAO = new MenuItemDAO();
    }

    public void testCreate() throws Exception {
        Assert.assertTrue(menuItemDAO.create(menuItem1));
        Assert.assertTrue(menuItemDAO.create(menuItem2));
        Assert.assertTrue(menuItemDAO.create(menuItem3));
    }

    public void testUpdate() throws Exception {
        Assert.assertTrue(menuItemDAO.update(new MenuItem((long)7, Weekday.MONDAY, "1", (double)4)));
        Assert.assertTrue(menuItemDAO.update(new MenuItem((long)5, Weekday.TUESDAY, "2", (double)5)));
        Assert.assertTrue(menuItemDAO.update(new MenuItem((long)6, Weekday.WEDNESDAY, "3", (double)6)));
    }

    public void testLoad() throws Exception {
        Assert.assertTrue(menuItemDAO.load((long)7) != null);
        Assert.assertTrue(menuItemDAO.load((long)8) == null);
    }

    public void testLoadAll() throws Exception {
        Assert.assertTrue(menuItemDAO.loadAll() != null);
    }

    public void testDelete() throws Exception {
        Assert.assertTrue(menuItemDAO.delete(menuItem1));
        Assert.assertTrue(menuItemDAO.delete(menuItem2));
        Assert.assertTrue(menuItemDAO.delete(menuItem3));
        Assert.assertTrue(menuItemDAO.load(menuItem1.getId()) == null);
        Assert.assertTrue(menuItemDAO.load(menuItem2.getId()) == null);
        Assert.assertTrue(menuItemDAO.load(menuItem2.getId()) == null);
    }
}
