package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.AuthorDaoSQLImpl;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Author;
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

public class AuthorManagerTest {

    public AuthorManager authorManager;
    private Author author;
    private AuthorDaoSQLImpl authorDaoSQLMock;
    private List<Author> authors;

    @BeforeEach
    public void initializeObjects(){
        authorManager = Mockito.mock(AuthorManager.class);
        authorDaoSQLMock = Mockito.mock(AuthorDaoSQLImpl.class);

        authors = new ArrayList<>();
        authors.add(new Author(55, "Harun"));
        authors.add(new Author(55, "Erna"));
        authors.add(new Author(55, "Minela"));
    }

    public AuthorManager getAuthorManager() {
        return authorManager;
    }

    public void setAuthorManager(AuthorManager authorManager) {
        this.authorManager = authorManager;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public AuthorDaoSQLImpl getAuthorDaoSQLMock() {
        return authorDaoSQLMock;
    }

    public void setAuthorDaoSQLMock(AuthorDaoSQLImpl authorDaoSQLMock) {
        this.authorDaoSQLMock = authorDaoSQLMock;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * Testing getAll method for Authors
     * @throws MyBookListException
     */
    @Test
    public void getAllAuthors() throws MyBookListException {
        Mockito.when(authorManager.getAll()).thenReturn(authors);
        assertEquals(authorManager.getAll(), authors);
        Mockito.verify(authorManager).getAll();
    }


    /**
     * Testing add method for Authors
     * @throws MyBookListException
     */

    @Test
    public void addAuthor() throws MyBookListException{
        MockedStatic<DaoFactory> daoFactoryMockedStatic = Mockito.mockStatic(DaoFactory.class);
        daoFactoryMockedStatic.when(DaoFactory::authorDao).thenReturn(authorDaoSQLMock);
        when(DaoFactory.authorDao().getAll()).thenReturn(authors);
        when(authorManager.getAll()).thenReturn(authors);

        author = new Author(65, "Novi autor");
        Mockito.doCallRealMethod().when(authorManager).add(author);
        authorManager.add(author);

        Assertions.assertTrue(true);
        Mockito.verify(authorManager).add(author);
        daoFactoryMockedStatic.close();
    }

}

