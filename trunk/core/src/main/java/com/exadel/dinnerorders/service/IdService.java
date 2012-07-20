package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.IdDAO;

/**
 * User: Dima Shulgin
 * Date: 20.07.12
 */
public class IdService {

    private static Long ID=new Long(92);

    synchronized public static Long getUniqueID() {
        if (ID == null) {
            loadLastUsedID();
        }
        Long retID = ID;
        ++ID;
        saveNewID();
        return retID;
    }

    private static void saveNewID() {
        IdDAO idDAO = new IdDAO();
        idDAO.setID(ID);
    }

    private static void loadLastUsedID() {
        IdDAO idDAO = new IdDAO();
        ID = idDAO.getID();
    }

}
