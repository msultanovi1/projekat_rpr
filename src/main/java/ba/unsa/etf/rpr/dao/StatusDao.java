package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;

import java.util.List;

public interface StatusDao extends Dao<Status> {

    List<Book> searchByStatus(String status);
/*
    List<Book> searchByScore(double score);

    List<Book> searchByUser(User user);

    List<Book> searchByAuthor(Author author);

 */
}
