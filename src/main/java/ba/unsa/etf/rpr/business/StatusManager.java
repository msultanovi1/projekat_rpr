package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

/**
 * Business logic layer for managing statuses
 * StatusManager is a class that models our work with Status table in the database
 * and validates all statuses before being sent to the database.
 */
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

    /**
     * Method that searches statuses by user
     * @param user which book statuses we want to search
     * @return list of statuses that belong to this user
     * @throws MyBookListException if there's no statuses linked to this user
     */
    public List<Status> searchByUser(User user) throws MyBookListException {
        return DaoFactory.statusDao().searchByUser(user);
    }

}
