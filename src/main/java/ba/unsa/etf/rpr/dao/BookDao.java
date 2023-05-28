package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Score;

import java.util.List;

public interface BookDao extends Dao<Book>{

    List<Book> searchByName(String name);

    List<Book> searchByGenre(String name);

    List<Book> searchByUIN(long UIN);

    List<Book> searchByScore(Score score);
}
