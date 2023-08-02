package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.domain.User;

import java.util.List;

public interface GenreDao extends Dao<Genre>{

    List<Genre> searchByName(String name);
}
