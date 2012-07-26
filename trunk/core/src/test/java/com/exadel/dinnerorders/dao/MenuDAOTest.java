package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.DateUtils;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MenuDAOTest {
    private static MenuDAO menuDAO;
    private static MenuItemDAO menuItemDAO;
    private static Menu menu;
    private static MenuItem[] items;
    private static Timestamp currentDate;
    private static Timestamp futureDate;

    @BeforeClass
    public static void setInitialData() throws Exception {
        initDates();
        menuDAO = new MenuDAO();
        menuItemDAO = new MenuItemDAO();
        initMenuItems();
        initCollections();
        for (MenuItem menuItem : items) {
            menuItemDAO.create(menuItem);
        }
    }

    @Test
    public void testCreateAndDelete() throws Exception {
        menu.setId(null);
        Assert.assertTrue(menuDAO.create(menu));
        Menu createdMenu = menuDAO.load(menu.getId());
        Assert.assertTrue(isEquals(menu, createdMenu));

        Assert.assertTrue(menuDAO.delete(menu));
        Assert.assertTrue(menuDAO.load(menu.getId()) == null);
    }

    @Test
    public void testUpdate() throws Exception {
        menu.setId(null);
        Assert.assertTrue(menuDAO.create(menu));
        Timestamp newStartDate = DateUtils.getCurrentTime();
        Timestamp newEndDate = DateUtils.getNextFridayTime();
        Menu expectedMenu = new Menu(menu.getId(), "ChangedName", newStartDate, newEndDate, menu.getItems());
        Assert.assertTrue(menuDAO.update(expectedMenu));
        Menu actualMenu = menuDAO.load(menu.getId());
        Assert.assertTrue(isEquals(expectedMenu, actualMenu));
        Assert.assertTrue(menuDAO.delete(menu));
    }

    @Test
    public void testLoadAll() throws Exception {
        menu.setId(null);
        Assert.assertTrue(menuDAO.create(menu));
        Collection<Menu> menus = menuDAO.loadAll();
        Assert.assertTrue(isEquals(menu, menus.iterator().next()));
        Assert.assertTrue(menuDAO.loadAll() != null);
        Assert.assertTrue(menuDAO.delete(menu));
    }

    @Test
    public void testLoad() throws Exception {
        menu.setId(null);
        Assert.assertTrue(menuDAO.create(menu));
        Menu actual = menuDAO.load(menu.getId());
        Assert.assertTrue(isEquals(actual, menu));
        actual = menuDAO.load(menu.getId() + 1);
        Assert.assertTrue(actual == null);
        Assert.assertTrue(menuDAO.delete(menu));
    }

    @AfterClass
    public static void deleteUsedDate() throws Exception {
        for (MenuItem item : items) {
            Assert.assertTrue(menuItemDAO.delete(item));
        }
    }

    private static void initCollections() {
        HashMap<Weekday, List<MenuItem>> map = new HashMap<Weekday, List<MenuItem>>();
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        menuItems.add(items[0]);
        map.put(Weekday.MONDAY, menuItems);
        menuItems = new ArrayList<MenuItem>();
        for (int i = 1; i < items.length; i++) {
            menuItems.add(items[i]);
            map.put(Weekday.TUESDAY, menuItems);
        }
        menu = new Menu(null, "cafe2", currentDate, futureDate, map);
    }

    private static void initMenuItems() {
        items = new MenuItem[5];
        items[0] = new MenuItem(null, Weekday.MONDAY, "Dish0", 0D);
        for (int i = 1; i < items.length; i++) {
            items[i] = new MenuItem(null, Weekday.TUESDAY, "Dish" + Integer.toString(i), (double)(i+1));
        }
    }

    private static void initDates() {
        currentDate = DateUtils.getCurrentMondayTime();
        futureDate = DateUtils.getCurrentFridayTime();
    }

    private boolean isEquals(Menu menu1, Menu menu2){
        try {
            Assert.assertTrue(menu1.getCafeName().equals(menu2.getCafeName()));
            Assert.assertTrue(menu1.getId().equals(menu2.getId()));
            Assert.assertTrue(menu1.getItems().size() == menu2.getItems().size());

            for (int i = 0; i < menu1.getItems().get(Weekday.MONDAY).size(); i++) {
                MenuItem expectedItem = menu1.getItems().get(Weekday.MONDAY).get(i);
                MenuItem actualItem = menu2.getItems().get(Weekday.MONDAY).get(i);
                Assert.assertTrue(expectedItem.getDescription().equals(actualItem.getDescription()));
                Assert.assertTrue(expectedItem.getCost().equals(actualItem.getCost()));
                Assert.assertTrue(expectedItem.getId().equals(actualItem.getId()));
                Assert.assertTrue(expectedItem.getWeekday() == actualItem.getWeekday());
            }

            for (int i = 0; i < menu1.getItems().get(Weekday.TUESDAY).size(); i++) {
                MenuItem expectedItem = menu1.getItems().get(Weekday.TUESDAY).get(i);
                MenuItem actualItem = menu2.getItems().get(Weekday.TUESDAY).get(i);
                Assert.assertTrue(expectedItem.getDescription().equals(actualItem.getDescription()));
                Assert.assertTrue(expectedItem.getCost().equals(actualItem.getCost()));
                Assert.assertTrue(expectedItem.getId().equals(actualItem.getId()));
                Assert.assertTrue(expectedItem.getWeekday() == actualItem.getWeekday());
            }
        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }
}
