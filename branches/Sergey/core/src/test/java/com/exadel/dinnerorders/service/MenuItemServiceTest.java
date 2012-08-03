package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Василий Силин
 * Date: 1.8.12
 */

public class MenuItemServiceTest {
    private MenuItemDAO menuItemDAO;
    private MenuItem menuItem;

    @Before
    public void setUp() throws Exception {
        menuItemDAO = new MenuItemDAO();
        menuItem = new MenuItem(null, Weekday.FRIDAY, "A", (double)1);
        menuItemDAO.create(menuItem);
    }

    @Test
    public void testDeleteAndSave() {
        try {
            MenuItemService.delete(menuItem);
            Assert.assertTrue(menuItemDAO.load(menuItem.getId()) == null);
            menuItem.setId(null);
            MenuItemService.save(menuItem);
            Assert.assertTrue(menuItemDAO.load(menuItem.getId()).equals(menuItem));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testUpdateAndLoad() {
        try{
            MenuItem updatedMenuItem = new MenuItem(menuItem.getId(), Weekday.FRIDAY, "A", (double)1);
            MenuItemService.update(updatedMenuItem);
            menuItem = MenuItemService.load(updatedMenuItem.getId());
            if(menuItem == null || !menuItem.equals(updatedMenuItem)){
                throw new Exception();
            }
            MenuItemService.delete(menuItem);
            menuItem = MenuItemService.load(updatedMenuItem.getId());
            if(menuItem == null || !menuItem.equals(updatedMenuItem)){
                throw new Exception();
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @After
    public void tearDown() throws Exception {
        menuItemDAO.delete(menuItem);
    }
}
