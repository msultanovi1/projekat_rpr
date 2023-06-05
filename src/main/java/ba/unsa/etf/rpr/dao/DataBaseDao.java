package ba.unsa.etf.rpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseDao {

    private static Connection connection;

    private DataBaseDao(){}

    public static Connection getInstance() throws SQLException{
        if(connection == null){
            connection = DriverManager.getConnection("", "", "");
        }
        return connection;
    }
}
