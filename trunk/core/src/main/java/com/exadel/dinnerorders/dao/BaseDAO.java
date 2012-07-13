package com.exadel.dinnerorders.dao;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * User: Сенсей
 * Date: 13.7.12
 */

public abstract class BaseDAO <E> implements DAO <E>{
    protected Connection connection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/dinnerorders", "root", "12345");
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }catch(SQLException e){
            System.out.println("Connection: SQLException caught");
            System.out.println("---");
            while (e != null){
                System.out.println("Message   : " + e.getMessage());
                System.out.println("SQLState  : " + e.getSQLState());
                System.out.println("ErrorCode : " + e.getErrorCode());
                System.out.println("---");
                e = e.getNextException();
            }
        }
        return connection;
    }

    protected void disconnect(Connection connection){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("Disconnect: SQLException caught");
            System.out.println("---");
            while (e != null){
                System.out.println("Message   : " + e.getMessage());
                System.out.println("SQLState  : " + e.getSQLState());
                System.out.println("ErrorCode : " + e.getErrorCode());
                System.out.println("---");
                e = e.getNextException();
            }
        }
    }
}
