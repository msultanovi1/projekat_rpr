package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

/**
 * Business logic layer for managing genres
 * GenreManager is a class that models our work with Genre table in the database
 * and validates all genres before being sent to the database.
 */
public class GenreManager implements Manager<Genre>{

    private static GenreManager instance = null;

    public static GenreManager getInstance(){
        if(instance == null){
            instance = new GenreManager();
        }
        return instance;
    }

    @Override
    public Genre getById(int id) throws MyBookListException {
        Genre genre = DaoFactory.genreDao().getById(id);
        if (genre == null) {
            throw new MyBookListException("Genre with selected ID not found.");
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() throws MyBookListException {
        List<Genre> genres = DaoFactory.genreDao().getAll();
        if(genres.isEmpty()){
            throw new MyBookListException("Database is empty, no genres found");
        }
        return genres;
    }

    @Override
    public void delete(int id) throws MyBookListException {
        getById(id);
        DaoFactory.genreDao().delete(id);
    }

    @Override
    public void update(Genre genre) throws MyBookListException {
        DaoFactory.genreDao().update(genre);
    }

    @Override
    public void add(Genre item) throws MyBookListException {
        for(Genre genre : getAll()){
            if(genre.getId() == item.getId()){
                System.out.println(" is already in database!");
            }
        }
        DaoFactory.genreDao().add(item);
    }

    /**
     * Method that searches genres by name
     * @param name of the genre
     * @return list of genres that fit criteria
     * @throws MyBookListException if the genre with this name can't be found
     */
    public List<Genre> searchByName(String name) throws MyBookListException {
        return DaoFactory.genreDao().searchByName(name);
    }
}
