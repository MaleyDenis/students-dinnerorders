package com.exadel.dinnerorders.entity;

import com.exadel.dinnerorders.exception.WorkflowException;
import com.exadel.dinnerorders.service.Configuration;
import com.mongodb.DB;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 * User: Dima Shulgin
 * Date: 29.07.12
 */
public class DefaultMongoConnectionProvider implements DefaultMongoProvider {
    private static Logger logger = Logger.getLogger(DefaultMongoConnectionProvider.class);

    public DB connection() {
        try {
            Mongo mongo = new Mongo(Configuration.getProperty(SystemResource.MONGODB_HOST),
                    Integer.parseInt(Configuration.getProperty(SystemResource.MONGODB_PORT)));
            return mongo.getDB(Configuration.getProperty(SystemResource.MONGODB_NAME));
        } catch (UnknownHostException e) {
            logger.error("Unknown host '" + Configuration.getProperty(SystemResource.MONGODB_HOST) + "'");
            throw new WorkflowException(e);
        }
    }
}
