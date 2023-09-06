package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Genre;

import java.util.List;

/**
 * An interface that implements the base Dao interface and provides additional methods to be implemented
 * by the DaoSqlImpl that works with the Genre table in the database
 */
public interface GenreDao extends Dao<Genre>{

    /**
     * Method that lets you search the Genre table by genre name
     * @param name of the genre
     * @return List of genres that fit the criteria
     */
    List<Genre> searchByName(String name);
}
