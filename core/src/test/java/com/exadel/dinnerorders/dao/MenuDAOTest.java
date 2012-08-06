package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.DateUtils;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class MenuDAOTest {
    private MenuDAO menuDAO;
    private MenuItemDAO menuItemDAO;
    private Menu menu;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private Timestamp startDate;
    private Timestamp endDate;

    @Before
    public void setUp() throws Exception {
        int testWeekReturn = 52;
        menuDAO = new MenuDAO();
        menuItemDAO = new MenuItemDAO();
        startDate = new Timestamp(DateUtils.getCurrentMondayTime().getTime() -
                DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK * testWeekReturn);
        endDate = new Timestamp(DateUtils.getCurrentFridayTime().getTime() -
                DateUtils.DAYS_IN_WEEK * DateUtils.MILLISECONDS_IN_DAY * testWeekReturn);
        startDate.setNanos(0);
        endDate.setNanos(0);
        menuItem1 = new MenuItem(null, Weekday.MONDAY, "Item1", 67d);
        menuItem2 = new MenuItem(null, Weekday.TUESDAY, "Item2", 21d);

        menuItemDAO.create(menuItem1);
        menuItemDAO.create(menuItem2);
        menu = new Menu(null, "InitialName",startDate, endDate, new HashMap<Weekday, List<MenuItem>>());
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
            String newName = "UpdatedName";
            Timestamp newStartDate = new Timestamp(startDate.getTime() + DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
            Timestamp newEndDate = new Timestamp(endDate.getTime() + DateUtils.MILLISECONDS_IN_DAY * DateUtils.DAYS_IN_WEEK);
            newStartDate.setNanos(0);
            newEndDate.setNanos(0);
            Menu updatedMenu = new Menu(menu.getId(), newName, newStartDate, newEndDate, menu.getItems());
            Assert.assertTrue(menuDAO.update(updatedMenu));
            menu = menuDAO.load(menu.getId());
            if(menu == null || !menu.equals(updatedMenu)) {
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
    public void testLoadByID() {
        try{
            Menu newMenu = menuDAO.load(menu.getId());
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
            Collection<Long> menusId = menuDAO.getAllMenuIds();
            Assert.assertTrue(menusId.size() > 0);
        } catch (Exception e){
            Assert.assertTrue(false);
        }
    }

    @After
    public void tearDown() {
        menuDAO.delete(menu);
        menuItemDAO.delete(menuItem1);
        menuItemDAO.delete(menuItem2);
    }
}
