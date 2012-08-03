package com.exadel.dinnerorders.entity;

import com.mongodb.DB;

/**
 * User: Dima Shulgin
 * Date: 30.07.12
 */
public interface DefaultMongoProvider {
    public DB connection();
}
