package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoSQLImpl implements AuthorDao{

    private Connection connection;
    private static AuthorDaoSQLImpl instance = null;

    public AuthorDaoSQLImpl(){
        try{
            this.connection = DataBaseDao.getInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static AuthorDaoSQLImpl getInstance() {
        if(instance == null) instance = new AuthorDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance = null;
    }

    @Override
    public Author getById(int id) {
        String query = "SELECT * FROM Author WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                rs.close();
                return author;
            }else{
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author add(Author author) {
        String insert = "INSERT INTO Author(name) VALUES(?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, author.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            author.setId(rs.getInt(1));
            return author;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author update(Author author) {
        String update = "UPDATE Author SET name = ? where id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(update);
            stmt.setObject(1, author.getName());
            stmt.setObject(2, author.getId());
            stmt.executeUpdate();
            return author;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Author WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(delete);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> getAll() {
        String query = "SELECT * FROM Author";
        List<Author> authors = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                authors.add(author);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> searchByName(String name) {
        List<Author> authors = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Author WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                authors.add(author);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return authors;
    }
}

