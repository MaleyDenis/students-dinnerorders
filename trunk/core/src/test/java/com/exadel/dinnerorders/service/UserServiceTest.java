package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.UserDAO;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;

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

            } else {
                Assert.assertTrue(false);
            }
        }
    }

    @Test
    public void testLoadAllUsersFromLdapForNotNull(){
        Collection<User> loadedUsers = UserService.loadAllUsersFromLdap();
        Assert.assertNotNull(loadedUsers);
    }

    @Test
    public void testLoadAllUsersFromLdap() {
        Collection<String> userNames = new LdapService().loadAll();
        Collection<User> loadedUsers = UserService.loadAllUsersFromLdap();
        int matches = 0;
        for (User user: loadedUsers) {
            if (userNames.contains(user.getUserName())) {
                matches++;
            }
        }
        Assert.assertEquals(userNames.size(), matches);
    }
}
