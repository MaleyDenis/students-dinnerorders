package com.exadel.dinnerorders.dao;

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
import java.util.Date;


public class MenuDAOTest {
    private MenuDAO menuDAO = new MenuDAO();
    private MenuItemDAO menuItemDAO = new MenuItemDAO();
    private Menu menu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private Timestamp date;

    @Before
    public void setUp() throws Exception {
        date = new Timestamp((new Date()).getTime());
        menuItem1 = new MenuItem(null, Weekday.MONDAY, "rrrrr", new Double(6.7));
        menuItem2 = new MenuItem(null, Weekday.TUESDAY, "ttttt", new Double(77));
        menuItemDAO.create(menuItem1);
        menuItemDAO.create(menuItem2);
        menu = new Menu(null, "Josh", new Timestamp(date.getTime() - 1000), new Timestamp(date.getTime() + 1000), new HashMap<Weekday, List<MenuItem>>());
        menu.addItem(menuItem1);
        menu.addItem(menuItem2);
        menuDAO.create(menu);
    }

    @Test
    public void testDeleteAndCreate() {
        try {
            Assert.assertTrue(menuDAO.delete(menu));
            Assert.assertTrue(menuDAO.load(menu.getId()) == null);
            menu.setId(null);
            Assert.assertTrue(menuDAO.create(menu));
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testUpdate() {
        try{
            menu.getDateEnd().setNanos(0);
            menu.getDateStart().setNanos(0);
            Menu updatedMenu = new Menu(menu.getId(), "Amerikano", menu.getDateStart(), menu.getDateEnd(), menu.getItems());// change: cafeName;
            Assert.assertTrue(menuDAO.update(updatedMenu));
            menu = menuDAO.load(menu.getId());
            if(menu == null || !menu.equals(updatedMenu)) { // check update
                throw new Exception();
            }
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoadAll() {
        try{
            Collection<Menu> menus = menuDAO.loadAll();
            Assert.assertTrue(menus != null && menus.size() > 0);
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoad() {
        try{
            Menu newMenu = menuDAO.load(menu.getId());
            menu.getDateStart().setNanos(0);
            menu.getDateEnd().setNanos(0);
            if(newMenu == null || !menu.equals(newMenu)){
                throw new Exception();
            }
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testCallMenuId () {
        try {
            Collection<Long> menusId = menuDAO.getMenuIds();
            Assert.assertTrue(menusId.size() > 0);
            for(Long id : menusId){
                Assert.assertNotNull(menuDAO.load(id));
            }
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @After
    public void tearDown() throws Exception {
        menuDAO.delete(menu);
        menuItemDAO.delete(menuItem1);
        menuItemDAO.delete(menuItem2);
    }
}
