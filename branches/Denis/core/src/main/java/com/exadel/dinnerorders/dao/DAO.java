package com.exadel.dinnerorders.dao;

import java.util.Collection;


public interface DAO <E> {
    public boolean create(E newItem) throws IllegalAccessException, InstantiationException;
    public boolean update(E item) throws IllegalAccessException, InstantiationException;
    public boolean delete(E item) throws IllegalAccessException, InstantiationException;
    public E load(Long value) throws IllegalAccessException, InstantiationException;
    public Collection<E> loadAll() throws IllegalAccessException, InstantiationException;
}
