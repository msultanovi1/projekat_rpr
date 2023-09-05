package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.BookDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BookManagerTest {

    private Book book;
    private BookManager bookManager;
    private List<Book> books;
    private BookDaoSQLImpl bookDaoSQLMock;

    private GenreManagerTest genreManagerTest;
    private AuthorManagerTest authorManagerTest;

    @BeforeEach
    public void initializeObjects() throws MyBookListException{
        genreManagerTest = new GenreManagerTest();
        genreManagerTest.initializeObjects();
        authorManagerTest = new AuthorManagerTest();
        authorManagerTest.initializeObjects();
        bookManager = Mockito.mock(BookManager.class);
        bookDaoSQLMock = Mockito.mock(BookDaoSQLImpl.class);

        books = new ArrayList<>();
        books.add(new Book(1, "Prva knjiga", 4564567, genreManagerTest.getGenreManager().getById(1), authorManagerTest.getAuthorManager().getById(1)));
        books.add(new Book(2, "Druga knjiga", 4464567, genreManagerTest.getGenreManager().getById(2), authorManagerTest.getAuthorManager().getById(2)));

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookManager getBookManager() {
        return bookManager;
    }

    public void setBookManager(BookManager bookManager) {
        this.bookManager = bookManager;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public BookDaoSQLImpl getBookDaoSQLMock() {
        return bookDaoSQLMock;
    }

    public void setBookDaoSQLMock(BookDaoSQLImpl bookDaoSQLMock) {
        this.bookDaoSQLMock = bookDaoSQLMock;
    }

    public GenreManagerTest getGenreManagerTest() {
        return genreManagerTest;
    }

    public void setGenreManagerTest(GenreManagerTest genreManagerTest) {
        this.genreManagerTest = genreManagerTest;
    }

    public AuthorManagerTest getAuthorManagerTest() {
        return authorManagerTest;
    }

    public void setAuthorManagerTest(AuthorManagerTest authorManagerTest) {
        this.authorManagerTest = authorManagerTest;
    }

    /**
     * Testing add method for Books
     * @throws MyBookListException
     */
    @Test
    public void addBook() throws MyBookListException{
        book = new Book(1, "BlaBla", 1221221, genreManagerTest.getGenreManager().getById(2), authorManagerTest.getAuthorManager().getById(2));
        bookManager.add(book);

        Assertions.assertTrue(true);
        Mockito.verify(bookManager).add(book);
    }

    /**
     * Testing delete method for Bokks
     * @throws MyBookListException
     */
    @Test
    public void deleteBook() throws MyBookListException {
        book = new Book(3, "WOW", 1233212, genreManagerTest.getGenreManager().getById(3), authorManagerTest.getAuthorManager().getById(3));
        Mockito.doCallRealMethod().when(bookManager).delete(book.getId());
        bookManager.delete(book.getId());

        Assertions.assertTrue(true);
        Mockito.verify(bookManager).delete(book.getId());
    }

}
