package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoSQLImpl implements BookDao{

    private Connection connection;

    public BookDaoSQLImpl(){
        try{
            this.connection = DataBaseDao.getInstance();
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
                book.setName(rs.getString("name"));
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
    public Book add(Book book) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Book(name, UIN, idGenre) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, book.getName());
            stmt.setLong(2,book.getUIN());
            stmt.setInt(3, book.getGenre().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            book.setId(rs.getInt(1));
            return book;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book update(Book book) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("UPDATE Book SET name = ?, UIN = ?, idGenre  = ? WHERE id = ?");
            stmt.setInt(4, book.getId());
            stmt.setString(1, book.getName());
            stmt.setLong(2, book.getUIN());
            stmt.setInt(3, book.getGenre().getId());
            stmt.executeUpdate();
            return book;
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
                book.setUIN(rs.getLong("UIN"));
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
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
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
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
    public Book searchByUIN(long UIN) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Book WHERE UIN = ?");
            stmt.setLong(1, UIN);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(UIN);
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
                rs.close();
                return book;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> searchByScore(double score) {
        List<Book> books = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Score WHERE score >= ?");
            stmt.setDouble(1, score);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(rs.getLong("UIN"));
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
    }
}
