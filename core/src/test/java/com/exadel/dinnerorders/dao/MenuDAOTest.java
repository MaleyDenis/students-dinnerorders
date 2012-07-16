package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 16.7.12
 */
public class MenuDAOTest extends TestCase {
    @Test
    public void test() throws Exception {
        MenuDAO menuDAO = new MenuDAO();
        HashMap<Weekday, List<MenuItem>> map = new HashMap<Weekday, List<MenuItem>>();
        ArrayList<MenuItem> list = new ArrayList<MenuItem>();
        list.add(new MenuItem((long)1, Weekday.FRIDAY, "A", (double)1));
        map.put(Weekday.FRIDAY, list);
        list = new ArrayList<MenuItem>();
        list.add(new MenuItem((long)2, Weekday.WEDNESDAY, "B", (double)2));
        list.add(new MenuItem((long)3, Weekday.TUESDAY, "C", (double)3));
        Menu menu = new Menu((long)1, "1", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), map);
        Assert.assertTrue(menuDAO.create(menu));
        Assert.assertTrue(menuDAO.update(new Menu((long)2, "2", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), map)));
        Assert.assertTrue(menuDAO.delete(menu));
    }
}
