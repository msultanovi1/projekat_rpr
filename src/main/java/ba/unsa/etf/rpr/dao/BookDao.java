package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;

import java.util.List;

public interface BookDao extends Dao<Book>{

    List<Book> searchByName(String name);

    List<Book> searchByGenre(Genre genre);

    Book searchByUIN(long UIN);

    List<Book> searchByAuthor(Author author);
}
