package com.exadel.dinnerorders.cache;

import net.sf.ashkay.ObjectCache;

import java.util.HashSet;

/**
 * User: Василий Силин
 * Date: 31.7.12
 */

public abstract class Cache<E>{
    protected ObjectCache cache;
    protected HashSet<Long> keys = new HashSet<Long>();

    public HashSet<Long> getKeys() {
        return keys;
    }

    public Object evict(Long id) {
        keys.remove(id);
        return cache.evict(id);
    }

    public void update(Object key, Object data) {
        cache.evict(key);
        cache.put(key, data);
    }

    public abstract E get(final Long key);
}
