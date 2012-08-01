package com.exadel.dinnerorders.cache;

import com.exadel.dinnerorders.entity.MenuItem;

import net.sf.ashkay.CreationException;
import net.sf.ashkay.ObjectCache;

import org.apache.log4j.Logger;

/**
 * User: Василий Силин
 * Date: 1.8.12
 */
public class MenuItemCache extends Cache<MenuItem> {
    private Logger logger = Logger.getLogger(MenuCache.class);
    private static MenuItemCache menuItemCache = new MenuItemCache();

    private MenuItemCache () {
        cache = new ObjectCache(new MenuItemFactory());
    }

    public static MenuItemCache getInstance () {
        return menuItemCache;
    }

    @Override
    public MenuItem get (Long key) {
        try {
            keys.add(key);
            return (MenuItem) cache.get(key);
        } catch (CreationException e) {
            logger.error(e);
        }
        return null;
    }
}
