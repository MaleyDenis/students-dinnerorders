package com.exadel.dinnerorders.entity;

import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 02.08.12
 */
public interface ExportStrategy {
    public String getFileName();
    public Collection  getCollection();
    public Class getClazz();

}
