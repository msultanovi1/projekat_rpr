package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

public class StatusManager implements Manager<Status>{

    private static StatusManager instance = null;

    public static StatusManager getInstance(){
        if(instance == null){
            instance = new StatusManager();
        }
        return instance;
    }
    @Override
    public Status getById(int id) throws MyBookListException {
        Status status = DaoFactory.statusDao().getById(id);
        if (status == null) {
            throw new MyBookListException("Status with selected ID not found.");
        }
        return status;
    }

    @Override
    public List<Status> getAll() throws MyBookListException {
        List<Status> statuses = DaoFactory.statusDao().getAll();
        if (statuses.isEmpty()) {
            throw new MyBookListException("Database is empty - no statuses found.");
        }
        return statuses;
    }

    @Override
    public void delete(int id) throws MyBookListException {
        getById(id);
        DaoFactory.statusDao().delete(id);
    }

    @Override
    public void update(Status status) throws MyBookListException {
        DaoFactory.statusDao().update(status);
    }

    @Override
    public void add(Status item) throws MyBookListException {
        for(Status status : getAll()){
            if(status.getId() == item.getId())
                System.out.println("is already in database!");
        }
        DaoFactory.statusDao().add(item);
    }

    public List<Status> searchByUser(User user) throws MyBookListException {
        return DaoFactory.statusDao().searchByUser(user);
    }

    public List<Status> searchByBook(Book book) throws MyBookListException {
        return DaoFactory.statusDao().searchByBook(book);
    }

    public Status searchByUserAndBook(User user, Book book) throws MyBookListException {
        return DaoFactory.statusDao().searchByUserAndBook(user, book);
    }
}
