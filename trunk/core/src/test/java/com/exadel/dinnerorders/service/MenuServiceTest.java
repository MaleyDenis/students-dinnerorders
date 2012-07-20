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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 18.7.12
 */

public class MenuServiceTest extends TestCase {
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu menu1;
    private Menu menu2;

    @Before
    protected void setUp() throws Exception {
        menuItemDAO = new MenuItemDAO();
        menuDAO = new MenuDAO();
        HashMap<Weekday, List<MenuItem>> map = new HashMap<Weekday, List<MenuItem>>();
        ArrayList<MenuItem> list = new ArrayList<MenuItem>();
        list.add(new MenuItem((long)1, Weekday.FRIDAY, "A", (double)1));
        map.put(Weekday.FRIDAY, list);
        list = new ArrayList<MenuItem>();
        list.add(new MenuItem((long)2, Weekday.WEDNESDAY, "B", (double)2));
        list.add(new MenuItem((long)3, Weekday.TUESDAY, "C", (double)3));
        menu1 = new Menu((long)1, "1", new Timestamp((long)134262 * 10000000 + 2791664), new Timestamp((long)134262 * 10000000 + 2791664 + 3000), map);
        Assert.assertTrue(menuDAO.create(menu1));
        menu2 = new Menu((long)2, "1", new Timestamp((long)134262 * 10000000 + 2791664), new Timestamp((long)134262 * 10000000 + 2791664 + 1000), map);
    }

    public void testFindMenuByDate() throws Exception {
        Assert.assertTrue(menuDAO.create(menu2));
        Assert.assertTrue(menuItemDAO.create(new MenuItem((long)1, Weekday.FRIDAY, "A", (double)1)));
        Assert.assertTrue(menuItemDAO.create(new MenuItem((long)2, Weekday.WEDNESDAY, "B", (double)2)));
        Assert.assertTrue(menuItemDAO.create(new MenuItem((long)3, Weekday.TUESDAY, "C", (double)3)));
        Timestamp date = new Timestamp((long)134262 * 10000000 + 2791664 + 2000);
        Collection<Menu> collection = MenuService.findMenuByDate(date);
        Assert.assertTrue(collection.size() == 1);
        Menu menu = collection.iterator().next();
        Assert.assertTrue(menu.getCafeName().equals(menu1.getCafeName()));
        Assert.assertTrue(menu.getId().equals(menu1.getId()));
        Assert.assertTrue(menu.getItems().size() == menu1.getItems().size());
        Assert.assertTrue(menu.getItems().get(Weekday.FRIDAY).size() == menu1.getItems().get(Weekday.FRIDAY).size());
        MenuItem menuItem1 = menu1.getItems().get(Weekday.FRIDAY).get(0);
        MenuItem menuItem = menu.getItems().get(Weekday.FRIDAY).get(0);
        Assert.assertTrue(menuItem.getDescription().equals(menuItem1.getDescription()));
        Assert.assertTrue(menuItem.getCost().equals(menuItem1.getCost()));
        Assert.assertTrue(menuItem.getId().equals(menuItem1.getId()));
        Assert.assertTrue(menuItem.getWeekday() == menuItem1.getWeekday());
        Assert.assertTrue(menuDAO.delete(menu1));
        Assert.assertTrue(menuDAO.delete(menu2));
        Assert.assertTrue(menuDAO.load(menu1.getId()) == null);
        Assert.assertTrue(menuDAO.load(menu2.getId()) == null);
        Assert.assertTrue(menuItemDAO.delete(new MenuItem((long)1, Weekday.FRIDAY, "A", (double)1)));
        Assert.assertTrue(menuItemDAO.delete(new MenuItem((long)2, Weekday.WEDNESDAY, "B", (double)2)));
        Assert.assertTrue(menuItemDAO.delete(new MenuItem((long)3, Weekday.TUESDAY, "C", (double)3)));
    }
}
