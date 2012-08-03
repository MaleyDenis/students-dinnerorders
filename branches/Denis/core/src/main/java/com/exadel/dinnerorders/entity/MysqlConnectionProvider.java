package com.exadel.dinnerorders.entity;

import java.sql.Connection;

/**
 * User: Dima Shulgin
 * Date: 26.07.12
 */
public interface MysqlConnectionProvider {
    public Connection connection();
}
