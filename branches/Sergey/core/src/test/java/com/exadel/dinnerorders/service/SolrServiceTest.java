package com.exadel.dinnerorders.service;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SolrServiceTest {
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu currentMenu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;
    private Timestamp startDate;

    @Before
    public void setUp() throws Exception {
        menuItemDAO = new MenuItemDAO();
        menuDAO = new MenuDAO();
        menuItem1 = new MenuItem(null, Weekday.FRIDAY, "Friday dish", 50d);
        menuItem2 = new MenuItem(null, Weekday.WEDNESDAY, "Wednesday dish", 30d);
        menuItem3 = new MenuItem(null, Weekday.TUESDAY, "Tuesday dish", 20d);
        menuItemDAO.create(menuItem1);
        menuItemDAO.create(menuItem2);
        menuItemDAO.create(menuItem3);
        startDate = new Timestamp(DateUtils.getCurrentMondayTime().getTime() -
                DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
        Timestamp endDate = new Timestamp(DateUtils.getCurrentFridayTime().getTime() -
                DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
        currentMenu = new Menu(null, "cafeName", startDate, endDate, new HashMap<Weekday, List<MenuItem>>());
        currentMenu.addItem(menuItem1);
        currentMenu.addItem(menuItem2);
        currentMenu.addItem(menuItem3);
        currentMenu.getDateStart().setNanos(0);//set nanosecond for good checking
        currentMenu.getDateEnd().setNanos(0);
        menuDAO.create(currentMenu);
        testReimportDatabase();
    }

    @Test
    public void testFindMenuByDate() {
        try {
            Collection<Menu> menus = SolrService.findMenuByDate(startDate);
            Assert.assertTrue(isOneEqualExist(menus));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    private boolean isOneEqualExist(Collection<Menu> menus) {
        for (Menu menu : menus) {
            if (currentMenu.equals(menu)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testLoadMenuItemByID() {
        try {
            Assert.assertTrue(SolrService.loadMenuItemById(menuItem1.getId()).equals(menuItem1));
            Assert.assertTrue(SolrService.loadMenuItemById(menuItem2.getId()).equals(menuItem2));
            Assert.assertTrue(SolrService.loadMenuItemById(menuItem3.getId()).equals(menuItem3));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoadMenuById() {
        try {
            Assert.assertTrue(SolrService.loadMenuById(currentMenu.getId()).equals(currentMenu));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoadAll () {
        try {
            Collection<Menu> menus = SolrService.loadAll();
            Assert.assertTrue(isOneEqualExist(menus));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testReimportDatabase() {
        final long WAIT_IMPORTING_TIME = 1500;
        Assert.assertTrue(SolrService.reimportDatabase());
        try {
            TimeUnit.MILLISECONDS.sleep(WAIT_IMPORTING_TIME);
        } catch (InterruptedException iException ) {
            throw new AssertionError();
        }
    }

    @Test
    public void testLoadCafeNames() {
        List<String> cafeNames = SolrService.loadCafeNames();
        Collection<Menu> menus = MenuService.loadAll();
        for (Menu menu : menus) {
            Assert.assertTrue(cafeNames.contains(menu.getCafeName()));
        }
    }

    @After
    public void tearDown() {
        Assert.assertTrue(menuDAO.delete(currentMenu));
        Assert.assertTrue(menuItemDAO.delete(menuItem1));
        Assert.assertTrue(menuItemDAO.delete(menuItem2));
        Assert.assertTrue(menuItemDAO.delete(menuItem3));
    }
}
