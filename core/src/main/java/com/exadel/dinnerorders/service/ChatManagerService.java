//package com.exadel.dinnerorders.service;
//
//import com.exadel.dinnerorders.entity.Message;
//import com.exadel.dinnerorders.entity.Topic;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class ChatManagerService {
//    private final List<Topic> topics;
//    private final List<Message> noTopicMessages;
//    private final ExecutorService taskExecutor;
//
//    public ChatManagerService() {
//        taskExecutor = Executors.newCachedThreadPool();
//        topics = new ArrayList<Topic>();
//        noTopicMessages = new ArrayList<Message>();
//    }
//
//    public ChatManagerService(Collection<Topic> topics) {
//        taskExecutor = Executors.newCachedThreadPool();
//        this.topics = new ArrayList<Topic>();
//        noTopicMessages = new ArrayList<Message>();
//        this.topics.addAll(topics);
//    }
//
//    public void addMessage(Message message) {
//        taskExecutor.submit(new SaveMessageTask(topics, noTopicMessages, message));
//    }
//
//    public Collection<Message> getMessages(Topic topic) {
//        try {
//            return taskExecutor.submit(new CopyMessagesTask(null)).get();
//        } catch (ExecutionException iException) {
//            return new ArrayList<Message>();
//        } catch (InterruptedException interrupted) {
//
//        }
//    }
//
//    private class SaveMessageTask implements Callable<Boolean> {
//        private final List<Topic> topics;
//        private final List<Message> noTopicMessages;
//        private final Message message;
//
//        private SaveMessageTask(List<Topic> topics, List<Message> noTopicMessages, Message message) {
//            this.topics = topics;
//            this.noTopicMessages = noTopicMessages;
//            this.message = message;
//        }
//
//        @Override
//        public Boolean call() throws Exception {
//            synchronized (message) {
//                if (message.getTopic() == null) {
//                    noTopicMessages.add(message);
//                } else {
//                    int index = topics.indexOf(message.getTopic());
//                    topics.get(index).addMessage(message);
//                }
//            }
//            return true;
//        }
//    }
//
//    private class CopyMessagesTask implements Callable<Collection<Message>> {
//        private final List<Message> messages;
//        public CopyMessagesTask(List<Message> messages) {
//            this.messages = messages;
//        }
//
//        @Override
//        public Collection<Message> call() throws Exception {
//            synchronized (messages) {
//                List<Message> newList = new ArrayList<Message>(messages.size());
//                newList.addAll(messages);
//                return newList;
//            }
//        }
//    }
//}
