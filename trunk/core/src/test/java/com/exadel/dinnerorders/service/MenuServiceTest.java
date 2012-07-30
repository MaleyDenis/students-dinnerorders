package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.exception.WorkflowException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuServiceTest {
    private static MenuDAO menuDAO;
    private static MenuItemDAO menuItemDAO;
    private static Menu menu1;
    private static Menu menu2;
    private static MenuItem[] items;
    private static Timestamp pastDate;
    private static Timestamp currentDate;
    private static Timestamp futureDate;

    @BeforeClass
    public static void installInitialData() throws Exception {
        initDates();
        menuDAO = new MenuDAO();
        menuItemDAO = new MenuItemDAO();
        initMenuItems();
        initCollections();
        for (MenuItem menuItem : items) {
            menuItemDAO.create(menuItem);
        }
        Assert.assertTrue(menuDAO.create(menu1));
        Assert.assertTrue(menuDAO.create(menu2));
    }

    @Test
    public void testFindMenuByDate() throws Exception {
        Timestamp date = DateUtils.getCurrentTime();
        Menu menu;
        try {
            menu = MenuService.findMenuByDate(date).iterator().next();
        } catch (WorkflowException wfException) {
            return;
        }
        Assert.assertTrue(menu.getCafeName().equals(menu1.getCafeName()));
        Assert.assertTrue(menu.getId().equals(menu1.getId()));
        Assert.assertTrue(menu.getItems().size() == menu1.getItems().size());
        Assert.assertTrue(menu.getItems().get(Weekday.MONDAY).size() == menu1.getItems().get(Weekday.MONDAY).size());
        MenuItem menuItem1 = menu1.getItems().get(Weekday.MONDAY).get(0);
        MenuItem menuItem = menu.getItems().get(Weekday.MONDAY).get(0);
        Assert.assertTrue(menuItem.getDescription().equals(menuItem1.getDescription()));
        Assert.assertTrue(menuItem.getCost().equals(menuItem1.getCost()));
        Assert.assertTrue(menuItem.getId().equals(menuItem1.getId()));
        Assert.assertTrue(menuItem.getWeekday() == menuItem1.getWeekday());
    }

    @Test
    public void testFindMenuForNextWeek() {
        Menu menu;
        try {
            menu = MenuService.findMenuForNextWeek().iterator().next();
        } catch (WorkflowException wfException) {
            return;
        }
        Assert.assertTrue(menu.getCafeName().equals(menu2.getCafeName()));
        Assert.assertTrue(menu.getId().equals(menu2.getId()));
        Assert.assertTrue(menu.getItems().size() == menu2.getItems().size());
        Assert.assertTrue(menu.getItems().get(Weekday.TUESDAY).size() == menu2.getItems().get(Weekday.TUESDAY).size());
        MenuItem menuItem1 = menu2.getItems().get(Weekday.TUESDAY).get(0);
        MenuItem menuItem = menu.getItems().get(Weekday.TUESDAY).get(0);
        Assert.assertTrue(menuItem.getDescription().equals(menuItem1.getDescription()));
        Assert.assertTrue(menuItem.getCost().equals(menuItem1.getCost()));
        Assert.assertTrue(menuItem.getId().equals(menuItem1.getId()));
        Assert.assertTrue(menuItem.getWeekday() == menuItem1.getWeekday());
    }

    @Test
    public void testFindMenuForCurrentWeek() {
        Menu menu;
        try {
            menu = MenuService.findMenuForCurrentWeek().iterator().next();
        } catch (WorkflowException wfException) {
            return;
        }
        Assert.assertTrue(menu.getCafeName().equals(menu1.getCafeName()));
        Assert.assertTrue(menu.getId().equals(menu1.getId()));
        Assert.assertTrue(menu.getItems().size() == menu1.getItems().size());
        Assert.assertTrue(menu.getItems().get(Weekday.MONDAY).size() == menu1.getItems().get(Weekday.MONDAY).size());
        MenuItem menuItem1 = menu1.getItems().get(Weekday.MONDAY).get(0);
        MenuItem menuItem = menu.getItems().get(Weekday.MONDAY).get(0);
        Assert.assertTrue(menuItem.getDescription().equals(menuItem1.getDescription()));
        Assert.assertTrue(menuItem.getCost().equals(menuItem1.getCost()));
        Assert.assertTrue(menuItem.getId().equals(menuItem1.getId()));
        Assert.assertTrue(menuItem.getWeekday() == menuItem1.getWeekday());
    }

    @AfterClass
    public static void deleteUsedData() {
        Assert.assertTrue(menuDAO.delete(menu1));
        Assert.assertTrue(menuDAO.delete(menu2));
        for (MenuItem item:items) {
            menuItemDAO.delete(item);
        }
    }

    private static void initCollections() {
        HashMap<Weekday, List<MenuItem>> map = new HashMap<Weekday, List<MenuItem>>();
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        menuItems.add(items[0]);
        map.put(Weekday.MONDAY, menuItems);
        menu1 = new Menu(null, "cafe1", pastDate, currentDate, map);

        map = new HashMap<Weekday, List<MenuItem>>();
        menuItems = new ArrayList<MenuItem>();
        for (int i = 1; i < items.length; i++) {
            menuItems.add(items[i]);
            map.put(Weekday.TUESDAY, menuItems);
        }

        menu2 = new Menu(null, "cafe2", currentDate, futureDate, map);
    }

    private static void initMenuItems() {
        items = new MenuItem[5];
        items[0] = new MenuItem(null, Weekday.MONDAY, "Dish0", 0D);
        for (int i = 1; i < items.length; i++) {
            items[i] = new MenuItem(null, Weekday.TUESDAY, "Dish" + Integer.toString(i), (double)(i+1));
        }
    }

    private static void initDates() {
        pastDate = DateUtils.getCurrentMondayTime();
        currentDate = DateUtils.getNextMondayTime();
        futureDate = DateUtils.getNextFridayTime();
    }
}
