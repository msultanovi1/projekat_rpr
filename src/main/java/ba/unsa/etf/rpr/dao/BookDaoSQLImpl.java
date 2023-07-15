package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookDaoSQLImpl extends AbstractDao<Book> implements BookDao{

    private static BookDaoSQLImpl instance = null;

    private BookDaoSQLImpl(){
        super("Book");
    }

    public static BookDaoSQLImpl getInstance() {
        if(instance == null) instance = new BookDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance = null;
    }


    @Override
    public Book rowToObject(ResultSet rs) throws MyBookListException {
        Book book = new Book();
        try {
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setUIN(rs.getLong("UIN"));
            book.setGenre(DaoFactory.genreDao().getById(rs.getInt("idGenre")));
            book.setAuthor(DaoFactory.authorDao().getById(rs.getInt("idAuthor")));
        }
        catch (SQLException error) {
            throw new MyBookListException(error.getMessage(), error);
        }
        return book;
    }

    @Override
    public Map<String, Object> objectToRow(Book book) {
        Map<String, Object> tableRow = new LinkedHashMap<>();
        tableRow.put("id", book.getId());
        tableRow.put("name", book.getName());
        tableRow.put("UIN", book.getUIN());
        tableRow.put("idGenre", book.getGenre().getId());
        tableRow.put("idAuthor", book.getAuthor().getId());
        return tableRow;
    }

    @Override
    public List<Book> searchByName(String name) {
        try {
            return super.executeQuery("SELECT * FROM Book WHERE name = ?", new Object[]{name});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Book> searchByGenre(Genre genre) {
        try {
            return super.executeQuery("SELECT * FROM Book WHERE idGenre = ?", new Object[]{genre.getId()});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Book searchByUIN(long UIN) {
        try {
            return super.executeQueryUnique("SELECT * FROM Book WHERE UIN = ?", new Object[]{UIN});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Book> searchByAuthor(Author author) {
        try {
            return super.executeQuery("SELECT * FROM Book WHERE idAuthor = ?", new Object[]{author.getId()});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }


}
