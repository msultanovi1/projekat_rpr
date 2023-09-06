package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;

import java.util.List;

/**
 * An interface that implements the base Dao interface and provides additional methods to be implemented
 * by the DaoSqlImpl that works with the Author table in the database
 */
public interface AuthorDao extends Dao<Author>{
    /**
     * Method that lets you search the Author table by author name
     * @param name of the author
     * @return List of Authors that fit the criteria
     */
    List<Author> searchByName(String name);
}
