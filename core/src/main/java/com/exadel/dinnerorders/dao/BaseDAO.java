package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.DbConnection;
import com.exadel.dinnerorders.entity.DefaultMongoConnectionProvider;
import com.exadel.dinnerorders.entity.MysqlConnectionProvider;
import com.exadel.dinnerorders.exception.WorkflowException;
import com.mongodb.DB;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;


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
    private SessionFactory sessionFactory;

    protected Connection getConnection(DAO dao)  {
        Annotation annotation = dao.getClass().getAnnotation(DbConnection.class);
        DbConnection dbConnection = (DbConnection) annotation;
        try {
            return ((MysqlConnectionProvider) dbConnection.connectionType().newInstance()).connection();
        } catch (InstantiationException e) {
            logger.error("Error! Connection hasn't been returned",e);
            throw new WorkflowException(e);
        } catch (IllegalAccessException e) {
            logger.error("Error! Connection hasn't been returned",e);
            throw new WorkflowException(e);
        }
    }
    protected SessionFactory getSessionFactory(DAO dao){
        Annotation annotation = dao.getClass().getAnnotation(DbConnection.class);
        DbConnection dbConnection = (DbConnection)annotation;
        try{
            return ((MysqlConnectionProvider) dbConnection.connectionType().newInstance()).sessionFactory();
        } catch (InstantiationException e) {
            logger.error("Error! Connection hasn't been returned",e);
            throw new WorkflowException(e);
        } catch (IllegalAccessException e) {
            logger.error("Error! Connection hasn't been returned",e);
            throw new WorkflowException(e);
        }

    }

    protected DB getMongoDB(DAO dao) {
        Annotation annotation = dao.getClass().getAnnotation(DbConnection.class);
        DbConnection dbConnection = (DbConnection) annotation;
        try {
            return ((DefaultMongoConnectionProvider) dbConnection.connectionType().newInstance()).connection();
        } catch (InstantiationException e) {
            logger.error("Error! Connection hasn't been returned",e);
            throw new WorkflowException(e);
        } catch (IllegalAccessException e) {
            logger.error("Error! Connection hasn't been returned",e);
            throw new WorkflowException(e);
        }
    }

    protected void disconnect(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("BaseDAO: getConnection has disconnected with errors.", e);
        }
    }

    public Long getID()  {
        Connection connection = getConnection(this);
        try {
            CallableStatement callableStatement = connection.prepareCall("{call getID(?)}");
            callableStatement.registerOutParameter("idOUT", Types.BIGINT);
            boolean hadResults = callableStatement.execute();
            if (!hadResults) {
                return callableStatement.getLong(1);
            }
        } catch (SQLException e) {
            logger.error("Error , value hasn't been returned", e);
        } finally {
            disconnect(connection);
        }
        throw new WorkflowException();
    }
}