package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.service.SolrService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

public class ReimportDatabaseTask extends Task {
    private final Logger logger = Logger.getLogger(ReimportDatabaseTask.class);

    public ReimportDatabaseTask() {
        super();
    }

    @Override
    public Boolean call() {
        Boolean result = true;
        if (lastExecutionTime != null) {
            result = SolrService.reimportDatabase();
        }
        lastExecutionTime = new Timestamp(System.currentTimeMillis());
        logger.trace("ReimportDatabaseTask: task executed at" + lastExecutionTime + ". Result: " + result);
        return result;
    }
}
