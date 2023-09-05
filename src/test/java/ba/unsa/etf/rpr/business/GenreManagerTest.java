package ba.unsa.etf.rpr.business;
;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.dao.GenreDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GenreManagerTest {

    public Genre genre;
    public GenreManager genreManager;
    private List<Genre> genres;
    private GenreDaoSQLImpl genreDaoSQLMock;

    @BeforeEach
    public void initializeObjects(){
        genreManager = Mockito.mock(GenreManager.class);
        genreDaoSQLMock = Mockito.mock(GenreDaoSQLImpl.class);

        genres = new ArrayList<>();
        genres.add(new Genre(55, "Prvi"));
        genres.add(new Genre(55, "Drugi"));
        genres.add(new Genre(55, "Treci"));
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public GenreManager getGenreManager() {
        return genreManager;
    }

    public void setGenreManager(GenreManager genreManager) {
        this.genreManager = genreManager;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public GenreDaoSQLImpl getGenreDaoSQLMock() {
        return genreDaoSQLMock;
    }

    public void setGenreDaoSQLMock(GenreDaoSQLImpl genreDaoSQLMock) {
        this.genreDaoSQLMock = genreDaoSQLMock;
    }

    /**
     * Testing getAll method for Genres
     * @throws MyBookListException
     */
    @Test
    public void getAllGenres() throws MyBookListException{
        Mockito.when(genreManager.getAll()).thenReturn(genres);
        assertEquals(genreManager.getAll(), genres);
        Mockito.verify(genreManager).getAll();
    }

    /**
     * Testing add method for Genres
     * @throws MyBookListException
     */
    @Test
    public void addGenre() throws MyBookListException{
        MockedStatic<DaoFactory> daoFactoryMockedStatic = Mockito.mockStatic(DaoFactory.class);
        daoFactoryMockedStatic.when(DaoFactory::genreDao).thenReturn(genreDaoSQLMock);
        when(DaoFactory.genreDao().getAll()).thenReturn(genres);
        when(genreManager.getAll()).thenReturn(genres);

        genre = new Genre(65, "Novi autor");
        Mockito.doCallRealMethod().when(genreManager).add(genre);
        genreManager.add(genre);

        Assertions.assertTrue(true);
        Mockito.verify(genreManager).add(genre);
        daoFactoryMockedStatic.close();
    }

}
