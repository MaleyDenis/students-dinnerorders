package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MessageDAO;
import com.exadel.dinnerorders.entity.Message;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

public class MessageService {
    private final static MessageDAO messageDAO = new MessageDAO();

    public static boolean save(Message message) {
        return messageDAO.create(message);
    }

    public static boolean update(Message message) {
        return messageDAO.update(message);
    }

    public static boolean delete(Message message) {
        return messageDAO.delete(message);
    }

    public static Message loadByID(Long ID) {
        return messageDAO.load(ID);
    }

    public static Collection<Message> loadAll() {
        return messageDAO.loadAll();
    }

    public static Collection<Message> loadMessagesByDate(Timestamp startDate, Timestamp endDate) {
        Predicate<Message> predicate = createPredicate(startDate, endDate);
        return Collections2.filter(loadAll(), predicate);
    }

    private static Predicate<Message> createPredicate(final Timestamp startDate, final Timestamp endDate) {
        return new Predicate<Message>() {
            public boolean apply(@Nullable Message o) {
                return o != null && ((o.getDate().equals(startDate) || o.getDate().after(startDate))
                        || ((o.getDate().equals(endDate) || o.getDate().before(endDate))) );
            }
        };
    }

    public static Collection<Message> loadTodayMessages() {
        Calendar morningTime = Calendar.getInstance();
        Calendar eveningTime = Calendar.getInstance();
        morningTime.set(Calendar.HOUR, 0);
        morningTime.set(Calendar.MINUTE, 0);
        morningTime.set(Calendar.SECOND, 0);
        morningTime.set(Calendar.MILLISECOND, 0);
        eveningTime.set(Calendar.AM_PM, 1);
        eveningTime.set(Calendar.HOUR, 11);
        eveningTime.set(Calendar.MINUTE, 59);
        eveningTime.set(Calendar.SECOND, 59);
        eveningTime.set(Calendar.MILLISECOND, 999);
        return loadMessagesByDate(new Timestamp(morningTime.getTimeInMillis()),
                                  new Timestamp(eveningTime.getTimeInMillis()));
    }
}
