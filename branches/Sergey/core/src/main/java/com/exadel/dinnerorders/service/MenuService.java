package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.cache.MenuCache;
import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuService {
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

    public static Collection<Menu> findMenuBeforeDate(final Timestamp date){
        Predicate<Menu> predicate = new Predicate<Menu>() {
            public boolean apply(@Nullable Menu o) {
                return o != null && ((o.getDateStart().before(date) || (o.getDateStart().equals(date) )));
            }
        };
        return Collections2.filter(loadAll(), predicate);
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

    public static boolean merge(Menu menu) {
        Timestamp searchingDate = new Timestamp(menu.getDateStart().getTime());
        Collection<Menu> loaded = MenuService.findMenuByDate(searchingDate);
        Menu existed = isNewMenu(menu, loaded);
        if (existed == null) {
            return MenuService.save(menu);
        } else {
            menu.setId(existed.getId());
            return MenuService.update(menu);
        }
    }

    private static Menu isNewMenu(Menu menu, Collection<Menu> loaded) {
        for (Menu loadedMenu : loaded) {
            if (loadedMenu.getCafeName().equals(menu.getCafeName())) {
                return loadedMenu;
            }
        }
        return null;
    }

    public static boolean save(Menu newMenu) {

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
        Collection<Long> menuIds = menuDAO.getAllMenuIds();

        MenuCache cache = MenuCache.getInstance();
        menuIds.removeAll(cache.getKeys());

        Collection<Menu> menus = new ArrayList<Menu>();
        for(Long key : cache.getKeys()){
            menus.add(cache.get(key));
        }
        for(Long id : menuIds){
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

    public static boolean deleteAll(Collection<Menu> menus) {
        boolean result = true;
        for (Menu menu : menus) {
            result = result && delete(menu);
        }
        return result;
    }

    public static Menu findMenuByDateAndCafename(String cafename, Timestamp menuDate) {
        Collection<Menu> menus = findMenuByDate(menuDate);
        for (Menu menu:menus) {
            if (menu.getCafeName().equals(cafename)) {
                return menu;
            }
        }
        return null;
    }
}
