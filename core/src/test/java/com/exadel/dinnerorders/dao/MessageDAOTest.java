package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Content;
import com.exadel.dinnerorders.entity.Message;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.UserService;
import junit.framework.Assert;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collection;

public class MessageDAOTest {
    private static UserDAO userDAO;
    private static MessageDAO messageDAO;
    private static Content content1;
    private static Content content2;
    private static Message message;
    private static User user;

    @BeforeClass
    public static void initUser() {
        userDAO = new UserDAO();
        messageDAO = new MessageDAO();
        user = new User(null, "ldaplogin", "Unreal User", Role.ADMIN);
        userDAO.create(user);

        content1 = new Content();
        content1.setUrl("content1");

        content2 = new Content();
        content2.setUrl("content2");
    }

    @Before
    public void setUp() {
        message = new Message();
        message.setDate(DateUtils.getCurrentTime());
        message.setText("Message1");
        message.setUser(user);

        message.addContent(content1);
        message.addContent(content2);
        Assert.assertTrue(messageDAO.create(message));
    }

    @Test
    public void testCreate() {
        Assert.assertTrue(messageDAO.delete(message));
        message.setId(null);
        Assert.assertTrue(messageDAO.create(message));
        Message loaded = messageDAO.load(message.getId());
        Assert.assertTrue(isEquals(loaded, message));
    }

    private boolean isEquals(Message loaded, Message message) {
        boolean result = loaded.getId().equals(message.getId());
        result = result && loaded.getDate().equals(message.getDate());
        result = result && loaded.getText().equals(message.getText());
        result = result && loaded.getUser().getUserName().equals(message.getUser().getUserName());
        result = result && loaded.getUser().getLdapLogin().equals(message.getUser().getLdapLogin());
        result = result && loaded.getUser().getId().equals(message.getUser().getId());
        result = result && loaded.getUser().getRole() == (message.getUser().getRole());
        result = result && loaded.getContentList().size() == message.getContentList().size();
        return result;
    }

    @Test
    public void testLoadById() {
        Message loaded = messageDAO.load(message.getId());
        Assert.assertTrue(isEquals(loaded, message));
    }

    @Test
    public void testLoadAll() {
       Collection<Message> messages = new MessageDAO().loadAll();
        Assert.assertTrue(isOneContains(messages));
    }

    private boolean isOneContains(Collection<Message> messages) {
        for (Message loaded : messages) {
            if (isEquals(loaded, message)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testUpdate() {
        Message modified = new Message();
        modified.setId(message.getId());
        modified.setContentList(message.getContentList());
        modified.setTopic(message.getTopic());
        modified.setUser(message.getUser());

        //modifying
        message.setDate(DateUtils.getCurrentTime());
        modified.setDate(message.getDate());
        modified.setText("New messageText");
        message.setText("New messageText");

        messageDAO.update(message);
        message = messageDAO.load(message.getId());

        Assert.assertTrue(isEquals(modified, message));
    }

    @Test
    public void testInsert() {
        Message message1 = new Message();
        message1.setUser(UserService.findUserByID(43L));
        message1.setDate(DateUtils.getCurrentTime());
        message1.setText("This message doesn't contains any external content.\nIt was created to test parsing");
        message1.setContentList(new ArrayList<Content>());

        Message message2 = new Message();
        message2.setUser(UserService.findUserByID(43L));
        message2.setDate(DateUtils.getCurrentTime());
        message2.setText("");
        ArrayList<Content> list1 = new ArrayList<Content>();
        Content content3 = new Content();
        content3.setId(messageDAO.getID());
        content3.setUrl("http://media.treehugger.com/assets/images/2011/10/ec-rnd-005.jpg");
        list1.add(content3);
        Content content4 = new Content();
        content4.setId(messageDAO.getID());
        content4.setUrl("http://openclipart.org/image/250px/svg_to_png/116785/yellow_convertible_sports_car.png");
        list1.add(content4);
        message2.setContentList(list1);
        messageDAO.create(message1);
        messageDAO.create(message2);
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(messageDAO.delete(message));
    }

    @After
    public void tearDown() {
        messageDAO.delete(message);
    }

    @AfterClass
    public static void finishTesting() {
        userDAO.delete(user);
    }
}
