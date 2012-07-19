package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.UserDAO;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Dima Shulgin
 * Date: 19.07.12
 */
public class UserServiceTest {
    @Test
    public void testFindUserbyUserName() throws Exception {

        UserDAO userDAO = new UserDAO();
        long id = 345;
        User user = new User(id,"userLogin","userName", Role.ADMIN);
        userDAO.create(user);
        User user2 = UserService.findUserbyUserName("userName");
        if (user2 != null) {
            if (user2.getUserName().equals("userName")) {
                Assert.assertTrue(true);
                userDAO.dropTable();
            } else {
                Assert.assertTrue(false);
            }
        }
    }
}
