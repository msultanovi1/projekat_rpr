package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenreDaoSQLImpl extends AbstractDao<Genre> implements GenreDao {


    private static GenreDaoSQLImpl instance = null;

    private GenreDaoSQLImpl() {
        super("Genre");
    }

    public static GenreDaoSQLImpl getInstance() {
        if (instance == null) instance = new GenreDaoSQLImpl();
        return instance;
    }

    public static void removeInstance() {
        if (instance != null)
            instance = null;
    }

    @Override
    public Genre rowToObject(ResultSet rs) throws MyBookListException {
        Genre genre = new Genre();
        try {
            genre.setId(rs.getInt("id"));
            genre.setName(rs.getString("name"));
        }
        catch (SQLException error) {
            throw new MyBookListException(error.getMessage(), error);
        }
        return genre;
    }

    @Override
    public Map<String, Object> objectToRow(Genre genre) {
        Map<String, Object> tableRow = new LinkedHashMap<>();
        tableRow.put("id", genre.getId());
        tableRow.put("name", genre.getName());
        return tableRow;
    }

}