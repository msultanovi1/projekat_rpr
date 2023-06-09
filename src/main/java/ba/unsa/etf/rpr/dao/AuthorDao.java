package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;

import java.util.List;

public interface AuthorDao extends Dao<Author>{
    List<Author> searchByName(String name);
}
