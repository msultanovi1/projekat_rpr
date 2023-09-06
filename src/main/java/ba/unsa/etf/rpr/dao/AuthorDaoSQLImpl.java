package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the implementation of the AuthorDao interface using MySQL
 * It contains all the methods that are needed to work with the Author table in the database
 */
public class AuthorDaoSQLImpl extends AbstractDao<Author> implements AuthorDao{


    private static AuthorDaoSQLImpl instance = null;

    private AuthorDaoSQLImpl(){
        super("Author");
    }

    /**
     * This method represents the implementation of the Singleton Design Pattern in this application
     * @return an instance of this class
     */
    public static AuthorDaoSQLImpl getInstance() {
        if(instance == null) instance = new AuthorDaoSQLImpl();
        return instance;
    }

    @Override
    public Author rowToObject(ResultSet rs) throws MyBookListException {
        Author author = new Author();
        try {
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
        }
        catch (SQLException error) {
            throw new MyBookListException(error.getMessage(), error);
        }
        return author;
    }

    @Override
    public Map<String, Object> objectToRow(Author author) {
        Map<String, Object> tableRow = new LinkedHashMap<>();
        tableRow.put("id", author.getId());
        tableRow.put("name", author.getName());
        return tableRow;
    }

    @Override
    public List<Author> searchByName(String name) {
        try {
            return super.executeQuery("SELECT * FROM Author WHERE name = ?", new Object[]{name});
        } catch (MyBookListException e) {
            throw new RuntimeException(e);
        }
    }

}

