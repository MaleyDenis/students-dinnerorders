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
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu menu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private Timestamp date;

    public boolean isEquals(Menu menu1, Menu menu2) {
        if(menu1 == menu2){
            return true;
        }
        if(menu1 == null || menu2 == null){
            return false;
        }
        if(!menu1.getId().equals(menu2.getId()) || !menu1.getCafeName().equals(menu2.getCafeName()) ||
                !menu1.getDateEnd().equals(menu2.getDateEnd()) || !menu1.getDateStart().equals(menu2.getDateStart())) {
            return false;
        }
        for(int i = 0;i < 5;i++){ //check items
            List<MenuItem> thisItems = menu1.getItems().get(Weekday.getWeekday(i + 1));
            List<MenuItem> menuItems = menu2.getItems().get(Weekday.getWeekday(i + 1));
            if(thisItems != null || menuItems != null) { //check on null
                if((thisItems == null && menuItems != null) || (menuItems == null && thisItems != null) || (thisItems.size() != menuItems.size()) || (!thisItems.containsAll(menuItems))){
                    return false;
                }
            }
        }
        return true;
    }

    @Before
    public void setUp() throws Exception {
        menuDAO = new MenuDAO();
        menuItemDAO = new MenuItemDAO();
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
            Assert.assertTrue(isEquals(menu, updatedMenu)); // check update
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
            if(newMenu == null || !isEquals(menu, newMenu)){
                throw new Exception();
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
