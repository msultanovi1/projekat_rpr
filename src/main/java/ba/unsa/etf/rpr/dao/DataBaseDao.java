package ba.unsa.etf.rpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseDao {

    private static Connection connection;

    private DataBaseDao(){}

    public static Connection getInstance() throws SQLException{
        if(connection == null){
            connection = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_MyBookList", "freedb_Minela", "7JmFy8t5cS&?T4K");
        }
        return connection;
    }
}
