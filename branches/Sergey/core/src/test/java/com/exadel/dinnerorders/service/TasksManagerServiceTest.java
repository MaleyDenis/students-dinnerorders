package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

public class TasksManagerServiceTest {
    private Timestamp startDate;
    private Timestamp endDate;
    private Menu menu;

    @Before
    public void setUp() {
        initDates();
        Map<Weekday, List<MenuItem>> map = createMenuItemsMap();
        menu = new Menu(null, "cafeName", startDate, endDate, map);
        Assert.assertTrue(MenuService.save(menu));
    }

    @Test
    public void testCleaning() {
        TasksManagerService tasksManagerService = new TasksManagerService();
        tasksManagerService.run();
        tasksManagerService.getLastExecutionResult();
        Collection<Menu> menus = MenuService.findMenuByDate(startDate);
        Assert.assertTrue(menus == null || menus.size() == 0);
    }

    private void initDates() {
        long elapsedTime = Long.parseLong(Configuration.getProperty(SystemResource.ELAPSED_TIME));
        startDate = new Timestamp(DateUtils.getCurrentMondayTime().getTime() - elapsedTime);
        endDate = new Timestamp(DateUtils.getCurrentFridayTime().getTime() - elapsedTime);
    }

    private Map<Weekday, List<MenuItem>> createMenuItemsMap() {
        Map<Weekday, List<MenuItem>> map = new HashMap<Weekday, List<MenuItem>>();
        for (int i = 1; i <= DateUtils.DEFAULT_WORK_DAYS; i++) {
            List<MenuItem> list = new ArrayList<MenuItem>();
            list.add(new MenuItem(null, Weekday.getWeekday(i), "Item" + i, 0d));
            map.put(Weekday.getWeekday(i), list);
        }
        return map;
    }

    @After
    public void tearDown() {
        MenuService.delete(menu);
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        for (int i = 1 ; i <= menu.getItems().size(); i++) {
            for (MenuItem menuItem : menu.getItems().get(Weekday.getWeekday(i))) {
                menuItemDAO.delete(menuItem);
            }
        }
    }
}
