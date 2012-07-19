package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.User;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alex Okunevich
 */
public class UserService {

    public static Collection<User> loadAllUsersFromLdap() {
        // 1. load all users from ldap
        // 2. load user from db with username (if user not exists - create user with (id == null, role USER)
        // 3. create collection
        return new ArrayList<User>();
    }

    public static User loadUserByUsername(String username) {
        return new User();
    }
}
