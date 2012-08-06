package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class MenuItemDAOTest {
    private static MenuItem menuItem;
    private static MenuItemDAO menuItemDAO;

    @Before
    public void setUp() throws Exception {
        menuItem = new MenuItem(null, Weekday.MONDAY, "1", 10d);
        menuItemDAO = new MenuItemDAO();
        menuItemDAO.create(menuItem);
    }

    @Test
    public void testDeleteAndCreate() {
        try{
            Assert.assertTrue(menuItemDAO.delete(menuItem));
            Assert.assertTrue(menuItemDAO.load(menuItem.getId()) == null);
            menuItem.setId(null);
            Assert.assertTrue(menuItemDAO.create(menuItem));
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testUpdate() {
        try{
            MenuItem updatedItem = new MenuItem(menuItem.getId(), Weekday.MONDAY, "11", 11d);
            Assert.assertTrue(menuItemDAO.update(updatedItem));
            menuItem = menuItemDAO.load(updatedItem.getId());
            if(menuItem == null || !menuItem.equals(updatedItem)){
                throw new Exception();
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoad() {
        try{
            MenuItem item = menuItemDAO.load(menuItem.getId());
            if(item == null || !menuItem.equals(item)){
                throw new Exception();
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoadAll() {
        try {
            Collection<MenuItem> items = menuItemDAO.loadAll();
            Assert.assertTrue(items != null && items.size() > 0);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @After
    public void tearDown() throws Exception {
        menuItemDAO.delete(menuItem);
    }
}
