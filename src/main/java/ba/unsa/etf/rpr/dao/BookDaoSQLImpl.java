package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.domain.Score;
import ba.unsa.etf.rpr.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoSQLImpl implements BookDao{

    private Connection connection;

    public BookDaoSQLImpl(){
        try{
            this.connection = DriverManager.getConnection("");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Book getById(int id) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Book WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getNString("name"));
                book.setUIN(rs.getLong("UIN"));
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
                rs.close();
                return book;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book add(Book item) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Book(name, UIN, idGenre) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getName());
            stmt.setLong(2,item.getUIN());
            stmt.setInt(3, item.getGenre().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            item.setId(rs.getInt(1));
            return item;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book update(Book item) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("UPDATE Book SET name = ?, UIN = ?, idGenre  = ? WHERE id = ?");
            stmt.setInt(4, item.getId());
            stmt.setString(1, item.getName());
            stmt.setLong(2, item.getUIN());
            stmt.setInt(3, item.getGenre().getId());
            stmt.executeUpdate();
            return item;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("DELETE from Book WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * from Book");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> searchByName(String name) {
        List<Book> users = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Book WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(rs.getLong("UIN"));
                users.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Book> searchByGenre(Genre genre) {
        List<Book> books = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Book WHERE idGenre = ?");
            stmt.setInt(1, genre.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(rs.getLong("UIN"));
                book.setGenre(genre);
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> searchByUIN(long UIN) {
        List<Book> books = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Book WHERE idGenre = ?");
            stmt.setLong(1, UIN);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(rs.getLong("UIN"));
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> searchByScore(Score score) {
        List<Book> books = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Book WHERE idScore = ?");
            stmt.setInt(1, score.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(rs.getLong("UIN"));
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
    }
}
