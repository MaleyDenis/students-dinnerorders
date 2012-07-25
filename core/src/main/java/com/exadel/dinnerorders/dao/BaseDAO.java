package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.exception.WorkflowException;
import com.exadel.dinnerorders.service.DefaultConnectionProvider;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * User: Василий Силин
 * Date: 13.7.12
 */

public abstract class BaseDAO<E> implements DAO<E> {
    private Logger logger = Logger.getLogger(BaseDAO.class);

    protected Connection connection(Object obj) {


        Annotation annotation = obj.getClass().getAnnotation(DbConnection.class);
        DbConnection dbConnection = (DbConnection) annotation;
        if(dbConnection!=null){

        if (dbConnection.connectionType().getSimpleName().equals("DefaultConnection"))
            return DefaultConnectionProvider.connection();
        }
        return  DefaultConnectionProvider.connection();
    }


    protected void disconnect(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("BaseDAO: connection has disconnected with errors.", e);
        }
    }

    public Long getID() {
        Connection connection = connection(this);

        try {

            CallableStatement callableStatement = connection.prepareCall("{call getID(?)}");
            callableStatement.registerOutParameter("idOUT", Types.INTEGER);
            boolean hadResults = callableStatement.execute();
            if (!hadResults)
                return callableStatement.getLong(1);


        } catch (SQLException e) {
            logger.error("Error , value hasn't been returned");
        } finally {
            disconnect(connection);
        }

        throw new WorkflowException();
    }
}


