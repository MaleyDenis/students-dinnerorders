package com.exadel.dinnerorders.entity;

import org.hibernate.SessionFactory;

import java.sql.Connection;

/**
 * User: Dima Shulgin
 * Date: 26.07.12
 */
public interface MysqlConnectionProvider {
    public Connection connection();
    public SessionFactory getSessionFactory();
}
