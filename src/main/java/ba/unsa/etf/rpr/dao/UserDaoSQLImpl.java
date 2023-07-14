package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserDaoSQLImpl extends AbstractDao<User> implements UserDao{

    private static UserDaoSQLImpl instance = null;

    private UserDaoSQLImpl(){
        super("User");
    }

    public static UserDaoSQLImpl getInstance() {
        if(instance == null) instance = new UserDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance = null;
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
    public User searchByNameAndPassword(String name, String password) {
        try {
            return super.executeQueryUnique("SELECT * FROM User WHERE name = ? AND password = ?", new Object[]{name, password});
        } catch (MyBookListException e) {
            throw new RuntimeException(e);
        }
    }


}
