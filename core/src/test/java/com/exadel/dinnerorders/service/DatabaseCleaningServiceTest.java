package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

public class DatabaseCleaningServiceTest {
    private Timestamp startDate;
    private Timestamp endDate;

    @Test(expected = IllegalArgumentException.class)
    public void testDatabaseCleaningServiceForException() {
        DatabaseCleaningService databaseCleaningService = new DatabaseCleaningService();
        databaseCleaningService.setIntervalDelay(-1000L);
        databaseCleaningService.stop();
    }

    @Test
    public void testDatabaseCleaningServiceForDeletion(){
        Map<Weekday, List<MenuItem>> map = createMenuItemsMap();
        initDates();
        Menu menu = new Menu(null, "cafename", startDate, endDate, map);
        Assert.assertTrue(MenuService.save(menu));
        final DatabaseCleaningService databaseCleaningService = new DatabaseCleaningService();
        databaseCleaningService.setStartDelay(0);
        databaseCleaningService.start();

        databaseCleaningService.waitCertainLaunch(0);
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
}
