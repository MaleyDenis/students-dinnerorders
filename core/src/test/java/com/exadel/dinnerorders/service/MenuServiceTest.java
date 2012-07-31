package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 18.7.12
 */

public class MenuServiceTest extends TestCase {
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu currentMenu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;

    @Before
    protected void setUp() throws Exception {
        menuItemDAO = new MenuItemDAO();
        menuDAO = new MenuDAO();
        menuItem1 = new MenuItem(null, Weekday.FRIDAY, "A", (double)1);
        menuItem2 = new MenuItem(null, Weekday.WEDNESDAY, "B", (double)2);
        menuItem3 = new MenuItem(null, Weekday.TUESDAY, "C", (double)3);
        menuItemDAO.create(menuItem1);
        menuItemDAO.create(menuItem2);
        menuItemDAO.create(menuItem3);
        currentMenu = new Menu(null, "1", new Timestamp((long)134262 * 10000000 + 2791664),
                new Timestamp((long)134262 * 10000000 + 2791664 + 3000), new HashMap<Weekday, List<MenuItem>>());
        currentMenu.addItem(menuItem1);
        currentMenu.addItem(menuItem2);
        currentMenu.addItem(menuItem3);
        currentMenu.getDateStart().setNanos(0);//set nanosecond for good checking
        currentMenu.getDateEnd().setNanos(0);
        menuDAO.create(currentMenu);
    }

    @Test
    public void testFindMenuByDate() {
        try {
            Timestamp date = new Timestamp((long)134262 * 10000000 + 2791664 + 2000);
            Collection<Menu> menus = MenuService.findMenuByDate(date);
            Menu menu = menus.iterator().next();
            Assert.assertTrue(menu != null);
            Assert.assertTrue(menu.equals(currentMenu));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testDeleteAndSave() {
        try {
            MenuService.delete(currentMenu);
            currentMenu.setId(null);
            menuItem1.setId(null);
            menuItem2.setId(null);
            menuItem3.setId(null);
            MenuService.save(currentMenu);
            Assert.assertTrue(menuDAO.load(currentMenu.getId()).equals(currentMenu));
            Assert.assertTrue(menuItemDAO.load(menuItem1.getId()).equals(menuItem1));
            Assert.assertTrue(menuItemDAO.load(menuItem2.getId()).equals(menuItem2));
            Assert.assertTrue(menuItemDAO.load(menuItem3.getId()).equals(menuItem3));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @After
    public void tearDown() throws Exception {
        menuDAO.delete(currentMenu);
        menuItemDAO.delete(menuItem1);
        menuItemDAO.delete(menuItem2);
        menuItemDAO.delete(menuItem3);
    }
}
