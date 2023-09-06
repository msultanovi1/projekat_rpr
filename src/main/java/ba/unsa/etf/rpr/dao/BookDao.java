package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;

import java.util.List;

/**
 * An interface that implements the base Dao interface and provides additional methods to be implemented
 * by the DaoSqlImpl that works with the Book table in the database
 */
public interface BookDao extends Dao<Book>{

    /**
     * Method that lets you search the Book table by book name
     * @param name of the Book
     * @return Book that fit the criteria
     */
    Book searchByName(String name);

    /**
     * Method that lets you search the Book table by book genre
     * @param genre of the Book
     * @return list of books that fit the criteria
     */
    List<Book> searchByGenre(Genre genre);

    /**
     * Method that lets you search the Book table by book UIN
     * @param UIN of the book
     * @return Book that fit the criteria
     */
    Book searchByUIN(long UIN);

    /**
     * Method that lets you search the Book table by book author
     * @param author of the Book
     * @return list of books that fit the criteria
     */
    List<Book> searchByAuthor(Author author);
}
