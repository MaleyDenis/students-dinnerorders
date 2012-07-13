package com.exadel.dinnerorders.dao;

/**
 * User: Сенсей
 * Date: 13.7.12
 */

public interface DAO <E> {
    public boolean create(E newItem);
    public boolean update(E item);
    public boolean delete(E item);
    public E read();
}
