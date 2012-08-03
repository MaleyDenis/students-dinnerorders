package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import org.junit.Test;

/**
 * User: Dima Shulgin
 * Date: 30.07.12
 */
public class MongoDAOTest {
    MongoDAO mongoDAO = new MongoDAO();

    @Test
    public void testCreate() throws Exception {
        long id = 1;
        User user = new User(id, "efdf", "testdsfdsfdsName1", Role.ADMIN);

        mongoDAO.create(user);

    }

    @Test
    public void testUpdate() throws Exception {
        long id = 1;
        User user = new User(id, "efdf", "dsfdf", Role.ADMIN);

        mongoDAO.update(user);

    }

    @Test
    public void testDelete() throws Exception {
        long id = 1;
        User user = new User(id, "efdf", "dsfdf", Role.ADMIN);

        mongoDAO.delete(user);

    }

    @Test
    public void testLoad() throws Exception {

    }

    @Test
    public void testLoadAll() throws Exception {

    }
}
