package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

public class AuthorManager implements Manager<Author> {

    private static AuthorManager instance = null;

    public static AuthorManager getInstance(){
        if(instance == null){
            instance = new AuthorManager();
        }
        return instance;
    }

    @Override
    public Author getById(int id) throws MyBookListException {
        Author author = DaoFactory.authorDao().getById(id);
        if(author == null){
            throw new MyBookListException("Author with selected ID not found.");
        }
        return author;
    }

    @Override
    public List<Author> getAll() throws MyBookListException {
        List<Author> authors = DaoFactory.authorDao().getAll();
        if(authors.isEmpty()){
            throw new MyBookListException("Database is empty, no authors found");
        }
        return authors;
    }

    @Override
    public void delete(int id) throws MyBookListException {
        getById(id);
        DaoFactory.authorDao().delete(id);
    }

    @Override
    public void update(Author author) throws MyBookListException {
        DaoFactory.authorDao().update(author);
    }

    @Override
    public void add(Author item) throws MyBookListException {
        for(Author author : getAll()){
            if(author.getId() == item.getId()){
                System.out.println(" is already in database!");
            }
        }
        DaoFactory.authorDao().add(item);
    }

    public List<Author> searchByName(String name) throws MyBookListException {
        return DaoFactory.authorDao().searchByName(name);
    }
}
