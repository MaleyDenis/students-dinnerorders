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

/**
 * User: Василий Силин
 * Date: 18.7.12
 */

public class MenuServiceTest {
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu currentMenu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;
    private Timestamp startDate;
    private Timestamp endDate;

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
        endDate = new Timestamp(DateUtils.getCurrentFridayTime().getTime() -
                DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
        currentMenu = new Menu(null, "cafeName", startDate, endDate, new HashMap<Weekday, List<MenuItem>>());
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
            Collection<Menu> menus = MenuService.findMenuByDate(startDate);
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
    public void testDeleteAndSave() {
        try {
            MenuService.delete(currentMenu);
            Assert.assertTrue(menuDAO.load(currentMenu.getId()) == null);
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

    @Test
    public void testUpdateAndLoad() {
        try{
            Menu updatedMenu = new Menu(currentMenu.getId(), "updatedCafeName",
                    currentMenu.getDateStart(), currentMenu.getDateEnd(), currentMenu.getItems());
            MenuService.update(updatedMenu);
            currentMenu = MenuService.load(currentMenu.getId());
            if(currentMenu == null || !currentMenu.equals(updatedMenu)){
                throw new Exception();
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoadAll () {
        try {
            Collection<Menu> menus = MenuService.loadAll();
            Assert.assertTrue(isOneEqualExist(menus));
        } catch (Exception e) {
            Assert.assertTrue(false);
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
