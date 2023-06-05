package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoSQLImpl implements UserDao{

    private Connection connection;

    public UserDaoSQLImpl(){
        try{
            this.connection = DataBaseDao.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM User WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setAboutMe(rs.getString("aboutMe"));
                rs.close();
                return user;
            }
            else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User add(User user) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO User(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            user.setId(rs.getInt(1));
            return user;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User user) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("UPDATE User SET name = ?, aboutMe = ? where id = ? ");
            stmt.setObject(1, user.getName());
            stmt.setObject(3, user.getId());
            stmt.setObject(2, user.getAboutMe());
            stmt.executeUpdate();
            return user;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("DELETE FROM User WHERE id = ?");
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM User";
        List<User> users = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAboutMe(rs.getString("aboutMe"));
                users.add(user);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM User WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAboutMe(rs.getString("aboutMe"));
                users.add(user);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}
