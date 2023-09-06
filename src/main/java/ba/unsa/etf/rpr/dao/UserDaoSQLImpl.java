package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the implementation of the UserDao interface using MySQL
 * It contains all the methods that are needed to work with the User table in the database
 */
public class UserDaoSQLImpl extends AbstractDao<User> implements UserDao{

    private static UserDaoSQLImpl instance = null;

    private UserDaoSQLImpl(){
        super("User");
    }

    /**
     * This method represents the implementation of the Singleton Design Pattern in this application
     * @return an instance of this class
     */
    public static UserDaoSQLImpl getInstance() {
        if(instance == null) {
            instance = new UserDaoSQLImpl();
        }
        return instance;
    }

    @Override
    public User rowToObject(ResultSet rs) throws MyBookListException {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setAboutMe(rs.getString("aboutMe"));
        }
        catch (SQLException error) {
            throw new MyBookListException(error.getMessage(), error);
        }
        return user;
    }

    @Override
    public Map<String, Object> objectToRow(User user) {
        Map<String, Object> tableRow = new LinkedHashMap<>();
        tableRow.put("id", user.getId());
        tableRow.put("name", user.getName());
        tableRow.put("password", user.getPassword());
        tableRow.put("aboutMe", user.getAboutMe());
        return tableRow;
    }

    @Override
    public List<User> searchByName(String name) {
        try {
            return super.executeQuery("SELECT * FROM User WHERE name = ?", new Object[]{name});
        } catch (MyBookListException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User searchByNameAndPassword(String name, String password) throws MyBookListException{
        return executeQueryUnique("SELECT * FROM User WHERE name = ? AND password = ?", new Object[]{name, password});
    }


}
