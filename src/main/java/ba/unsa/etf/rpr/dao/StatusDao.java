package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;

import java.util.List;

/**
 * An interface that implements the base Dao interface and provides additional methods to be implemented
 * by the DaoSqlImpl that works with the Status table in the database
 */
public interface StatusDao extends Dao<Status> {

    /**
     * Method that lets you search the Status table by user
     * @param user whose connection to a book and associated details are linked by a status.
     * @return list of statuses that connect this user and books that are in BookList of this user
     */
    List<Status> searchByUser(User user);

    /**
     * Method that lets you search the Status table by book
     * @param book which is going to be used to search status table
     * @return list of statuses that are connected to provided book
     */
    List<Status> searchByBook(Book book);

    /**
     * Method that lets you search the Status table by user and book
     * @param user of which BookList we want to search
     * @param book that we want to search and rest of the details about reading status of this book by user
     * @return status that connects user with the book and other associated details
     */
    Status searchByUserAndBook(User user, Book book);

}
