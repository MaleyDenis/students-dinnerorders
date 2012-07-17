package com.exadel.dinnerorders.dao;

import java.util.Collection;


public interface DAO <E> {
    public boolean create(E newItem);
    public boolean update(E item);
    public boolean delete(E item);
    public E load(Long value);
    public Collection<E> loadAll();
}
