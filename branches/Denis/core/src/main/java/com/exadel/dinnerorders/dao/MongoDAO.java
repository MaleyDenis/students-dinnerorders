package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultMongoConnectionProvider;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 29.07.12
 */
@DbConnection(connectionType = DefaultMongoConnectionProvider.class)
public class MongoDAO extends BaseDAO<User> {

    public boolean create(User newItem) throws IllegalAccessException, InstantiationException {
        DBCollection collection = getMongoDB(this).getCollection("users");
        BasicDBObject document = new BasicDBObject();
        document.put("ID", newItem.getId());
        document.put("LDAPLOGIN", newItem.getLdapLogin());
        document.put("USERNAME", newItem.getUserName());
        document.put("ROLE", newItem.getRole().name());
        collection.insert(document);
        return true;
    }

    public boolean update(User item) throws IllegalAccessException, InstantiationException {
        DBCollection collection = getMongoDB(this).getCollection("users");
        BasicDBObject document = new BasicDBObject();
        document.put("ID", item.getId());
        document.put("LDAPLOGIN", item.getLdapLogin());
        document.put("USERNAME", item.getUserName());
        document.put("ROLE", item.getRole().name());
        collection.update(new BasicDBObject().append("hosting", "hostB"), document);
        return false;
    }

    public boolean delete(User item) throws IllegalAccessException, InstantiationException {
        DBCollection collection = getMongoDB(this).getCollection("users");
        BasicDBObject document = new BasicDBObject();
        document.put("ID", item.getId());
        collection.remove(document);
        return false;
    }

    public User load(Long value) throws IllegalAccessException, InstantiationException {
        DBCollection collection = getMongoDB(this).getCollection("users");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", value);
        DBCursor cursor = collection.find(searchQuery);
        if (cursor.hasNext()) {
            DBObject user = cursor.next();
            return new User((User) user);
        }
        return null;
    }

    public Collection<User> loadAll() throws IllegalAccessException, InstantiationException {
        DBCollection collection = getMongoDB(this).getCollection("users");
        DBCursor cursor = collection.find();
        Collection<User> users = new ArrayList<User>();
        while (cursor.hasNext()){
            DBObject temp = cursor.next();
            users.add(new User(Long.parseLong(temp.get("ID").toString()),temp.get("LDAPLOGIN").toString(),temp.get("USERNAME").toString(),(Role)temp.get("ROLE")));
        }
        return users;
    }
}
