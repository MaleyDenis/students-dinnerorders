package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;


/**
 * User: Василий Силин
 * Date: 16.7.12
 */
public class MenuDAOTest extends TestCase {
    private MenuDAO menuDAO;
    private  Menu menu;

    @Before
    protected void setUp(){
        menuDAO = new MenuDAO();
        HashMap<Weekday, List<MenuItem>> map = new HashMap<Weekday, List<MenuItem>>();
        ArrayList<MenuItem> list = new ArrayList<MenuItem>();
        list.add(new MenuItem((long)1, Weekday.FRIDAY, "A", (double)1));
        map.put(Weekday.FRIDAY, list);
        list = new ArrayList<MenuItem>();
        list.add(new MenuItem((long)2, Weekday.WEDNESDAY, "B", (double)2));
        list.add(new MenuItem((long)3, Weekday.TUESDAY, "C", (double)3));
        menu = new Menu((long)1, "1", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), map);
    }

    @Test
    public void testCreate() throws Exception {
        Assert.assertTrue(menuDAO.create(menu));
    }

    @Test
    public void testUpdate() throws Exception{
        Assert.assertTrue(menuDAO.update(new Menu((long)2, "2", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), menu.getItems())));
    }

    @Test
    public void testLoadAll() throws Exception {
        Assert.assertTrue(menuDAO.loadAll() != null);
    }

    @Test
    public void testLoad() throws Exception {
        Assert.assertTrue(menuDAO.load((long)3242) != null);
        Assert.assertTrue(menuDAO.load((long)3252) == null);
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(menuDAO.delete(menu));
    }
}
