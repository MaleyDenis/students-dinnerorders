package com.exadel.dinnerorders.service;
import com.exadel.dinnerorders.dao.BaseDAO;

public class UniqueIDService {
    private static Long ID;

    synchronized public static Long getID() {
        if (ID == null) {
            loadLastUsedID();
        }
        Long retID = ID;
        ID++;
        saveNewID();
        return retID;
    }

    private static void saveNewID() {
        //Save new generated ID to DB;
    }

    private static void loadLastUsedID() {
        //Load from DB last used ID
    }

}
