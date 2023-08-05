package ba.unsa.etf.rpr.bussines;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

public class BookManager implements Manager<Book>{

    private static BookManager instance = null;

    public static BookManager getInstance(){
        if(instance == null){
            instance = new BookManager();
        }
        return instance;
    }

    @Override
    public Book getById(int id) throws MyBookListException {
        Book book = DaoFactory.bookDao().getById(id);
        if (book == null) {
            throw new MyBookListException("Book with selected ID not found.");
        }
        return book;
    }

    @Override
    public List<Book> getAll() throws MyBookListException {
        List<Book> books = DaoFactory.bookDao().getAll();
        if(books.isEmpty()){
            throw new MyBookListException("Database is empty, no books found");
        }
        return books;
    }

    @Override
    public void delete(int id) throws MyBookListException {
        getById(id);
        DaoFactory.bookDao().delete(id);
    }

    @Override
    public void update(Book book) throws MyBookListException {
        DaoFactory.bookDao().update(book);
    }

    @Override
    public void add(Book item) throws MyBookListException {
        for(Book book : getAll()){
            if(book.getId() == item.getId()){
                System.out.println(" is already in database!");
            }
        }
        DaoFactory.bookDao().add(item);
    }

    public Book searchByName(String name) throws MyBookListException {
        return DaoFactory.bookDao().searchByName(name);
    }

}
