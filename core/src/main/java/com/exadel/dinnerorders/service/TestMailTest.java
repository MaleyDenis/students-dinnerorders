package com.exadel.education.web.student.dao;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: 12
 * Date: 12.07.2012
 * Time: 16:47:54
 * To change this template use File | Settings | File Templates.
 */
public class TestMailTest {
    @Test
    public void testMain() throws Exception {
        TestMail tm = new TestMail();
        tm.send();
      org.junit.Assert.assertTrue(tm.isException()==false);
    }
}
