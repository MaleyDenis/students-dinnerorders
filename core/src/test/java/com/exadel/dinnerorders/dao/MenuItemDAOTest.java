package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

public class MenuItemDAOTest {
    private static MenuItem menuItem1;
    private static MenuItem menuItem2;
    private static MenuItem menuItem3;
    private static MenuItemDAO menuItemDAO;

    @BeforeClass
    public static void setUp() {
        menuItem1 = new MenuItem(null, Weekday.MONDAY, "1", 1D);
        menuItem2 = new MenuItem(null, Weekday.TUESDAY, "2", 2D);
        menuItem3 = new MenuItem(null, Weekday.WEDNESDAY, "3", 3D);
        menuItemDAO = new MenuItemDAO();
        Assert.assertTrue(menuItemDAO.create(menuItem1));
        Assert.assertTrue(menuItemDAO.create(menuItem2));
        Assert.assertTrue(menuItemDAO.create(menuItem3));
    }

    @Test
    public void testCreateAndDelete() throws Exception {
        MenuItem menuItem = new MenuItem(null, Weekday.THURSDAY, "4", 4D);
        Assert.assertTrue(menuItemDAO.create(menuItem));
        Assert.assertTrue(menuItemDAO.delete(menuItem));
        Assert.assertTrue(menuItemDAO.load(menuItem.getId()) == null);
    }

    @Test
    public void testUpdate() throws Exception {
        MenuItem modifiedItem = new MenuItem(menuItem1.getId(), Weekday.MONDAY, "11", 11D);
        Assert.assertTrue(menuItemDAO.update(modifiedItem));
        MenuItem actualItem = menuItemDAO.load(modifiedItem.getId());
        Assert.assertTrue(isEquals(actualItem, modifiedItem));
    }

    @Test
    public void testLoad() throws Exception {
        MenuItem expectedItem = menuItemDAO.load(menuItem2.getId());
        Assert.assertTrue(expectedItem != null);
        Assert.assertTrue(isEquals(expectedItem, menuItem2));
        Assert.assertTrue(menuItemDAO.load(menuItemDAO.getID()) == null);
    }

    @Test
    public void testLoadAll() throws Exception {
        Collection<MenuItem> loaded = menuItemDAO.loadAll();
        Assert.assertTrue(loaded != null);
    }

    @AfterClass
    public static void tearDown() {
        Assert.assertTrue(menuItemDAO.delete(menuItem1));
        Assert.assertTrue(menuItemDAO.delete(menuItem2));
        Assert.assertTrue(menuItemDAO.delete(menuItem3));
        Assert.assertTrue(menuItemDAO.load(menuItem1.getId()) == null);
        Assert.assertTrue(menuItemDAO.load(menuItem2.getId()) == null);
        Assert.assertTrue(menuItemDAO.load(menuItem2.getId()) == null);
    }

    private boolean isEquals(MenuItem item1, MenuItem item2){
        try {
            Assert.assertTrue(item1.getDescription().equals(item2.getDescription()));
            Assert.assertTrue(item1.getCost().equals(item2.getCost()));
            Assert.assertTrue(item1.getId().equals(item2.getId()));
            Assert.assertTrue(item1.getWeekday() == item2.getWeekday());

        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }
}
