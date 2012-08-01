package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.cache.MenuCache;
import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import org.apache.log4j.Logger;

import javax.annotation.Nullable;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 18.7.12
 */

public class MenuService {
    private Logger logger = Logger.getLogger(MenuService.class);
    private static MenuDAO menuDAO = new MenuDAO();
    private static MenuItemDAO menuItemDAO = new MenuItemDAO();

    public static Collection<Menu> findMenuForNextWeek() {
        Timestamp date = DateUtils.getNextMondayTime();
        return findMenuByDate(date);
    }

    public static Collection<Menu> findMenuForCurrentWeek(){
        Timestamp date = DateUtils.getCurrentMondayTime();
        return findMenuByDate(date);
    }

    public static Collection<Menu> findMenuByDate(final Timestamp date){
        Predicate<Menu> predicate = new Predicate<Menu>() {
            public boolean apply(@Nullable Menu o) {
                return o != null && ((o.getDateStart().before(date) && o.getDateEnd().after(date)) ||
                        (o.getDateStart().equals(date) || o.getDateEnd().equals(date)));
            }
        };
        Collection<Menu> result =  Collections2.filter(loadAll(), predicate);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public static boolean save(Menu newMenu){
        for(List<MenuItem> items : newMenu.getItems().values()){
            for(MenuItem item : items){
                if(!menuItemDAO.create(item)){
                    return false;
                }
            }
        }
        return menuDAO.create(newMenu);
    }

    public static Menu load(Long id){
        return MenuCache.getInstance().get(id);
    }

    public static Collection<Menu> loadAll () {
        Collection<Long> menusId = menuDAO.callMenuID();
        MenuCache cache = MenuCache.getInstance();
        menusId.removeAll(cache.getKeys());
        Collection<Menu> menus = new ArrayList<Menu>();
        for(Long key : cache.getKeys()){
            menus.add(cache.get(key));
        }
        for(Long id : menusId){
            menus.add(menuDAO.load(id));
        }
        return menus;
    }

    public static boolean delete(Menu menu) {
        for(List<MenuItem> items : menu.getItems().values()){
            for(MenuItem item : items){
                if(!menuItemDAO.delete(item)){
                    return false;
                }
            }
        }
        MenuCache.getInstance().evict(menu.getId());
        return menuDAO.delete(menu);
    }

    public static boolean update(Menu newMenu){
        MenuCache cache = MenuCache.getInstance();
        cache.update(newMenu.getId(), newMenu);
        return menuDAO.update(newMenu);
    }
}
