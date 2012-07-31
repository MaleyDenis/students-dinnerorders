package com.exadel.dinnerorders.cache;

import net.sf.ashkay.ObjectCache;

/**
 * User: Василий Силин
 * Date: 31.7.12
 */
public abstract class Cache<E> {
    protected ObjectCache cache;

    public abstract E get(final Long key);
}
