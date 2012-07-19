package com.exadel.dinnerorders.service;

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

        User user2 = UserService.findUserbyUserName("username");
        if (user2 != null) {
            if (user2.getUserName().equals("username")) {
                Assert.assertTrue(true);
            } else {
                Assert.assertTrue(false);
            }
        }
    }
}
