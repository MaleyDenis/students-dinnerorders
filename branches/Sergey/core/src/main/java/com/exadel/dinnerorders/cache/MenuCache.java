package com.exadel.dinnerorders.cache;

import com.exadel.dinnerorders.entity.Menu;

import net.sf.ashkay.CreationException;
import net.sf.ashkay.ObjectCache;

import org.apache.log4j.Logger;

/**
 * User: Василий Силин
 * Date: 31.7.12
 */

public class MenuCache extends Cache<Menu> {
    private Logger logger = Logger.getLogger(MenuCache.class);
    private static MenuCache menuCache = new MenuCache();

    private MenuCache () {
        cache = new ObjectCache(new MenuFactory());
    }

    public static MenuCache getInstance () {
        return menuCache;
    }

    @Override
    public Menu get (Long key) {
        try {
            keys.add(key);
            return (Menu) cache.get(key);
        } catch (CreationException e) {
            logger.error(e);
        }
        return null;
    }
}
