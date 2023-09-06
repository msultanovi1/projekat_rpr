package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

/**
 * Business logic layer for managing users
 * UserManager is a class that models our work with User table in the database
 * and validates all users before being sent to the database.
 */
public class UserManager implements Manager<User> {

    private static UserManager instance = null;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    @Override
    public User getById(int id) throws MyBookListException {
        User user = DaoFactory.userDao().getById(id);
        if (user == null) {
            throw new MyBookListException("User with selected ID not found.");
        }
        return user;
    }

    @Override
    public List<User> getAll() throws MyBookListException{
        List<User> users = DaoFactory.userDao().getAll();
        if (users.isEmpty()) {
            throw new MyBookListException("Database is empty - no users found.");
        }
        return users;
    }

    @Override
    public void delete(int id) throws MyBookListException {
        getById(id);
        DaoFactory.userDao().delete(id);
    }

    @Override
    public void update(User user) throws MyBookListException{
        DaoFactory.userDao().update(user);
    }

    @Override
    public void add(User item) throws MyBookListException{
        for(User user : getAll()){
            if(user.getName().equals(item.getName()))
                System.out.println("is already in database!");
        }
        DaoFactory.userDao().add(item);
    }

    /**
     * Method that validates the name and the password and searches for the user with matching parameters in the database
     * @param name of the user
     * @param password of the user
     * @return user with provided name and password
     * @throws MyBookListException if provided name and password are not matching any user credentials in the database
     */
    public User getByNameAndPassword(String name, String password) throws MyBookListException{
        try {
            checkNameAndPassword(name, password);
            User user = DaoFactory.userDao().searchByNameAndPassword(name, password);
            if (user == null) {
                throw new MyBookListException("No matches for provided name and password in database found.");
            }
            return user;
        }
        catch (MyBookListException exception) {
            throw new MyBookListException("Unsuccessful login|" + exception.getMessage());
        }
    }

    /**
     * Method that checks the format of name and password of a user
     * @param name whose format is checked
     * @param password whose format is checked
     * @throws MyBookListException if one of the parameters or both do not satisfy the required format
     */
    void checkNameAndPassword(String name, String password) throws MyBookListException {
        if (name.length() > 64) {
            throw new MyBookListException("Name mustn't be longer than 64 characters.");
        }
        if (password.length() < 8 || password.length() > 64) {
            throw new MyBookListException("Password must be between 8 and 64 characters long.");
        }
    }

    /**
     * Method that modifies the name and/or password of the user sent through the parameter
     * @param user whose name and/or password we want to update
     * @throws MyBookListException if new users name and password do not pass validation
     */
    public void updateNameAndPassword(User user) throws MyBookListException {
        try {
            checkNameAndPassword(user.getName(), user.getPassword());
            DaoFactory.userDao().update(user);
        }
        catch (MyBookListException exception) {
            if (exception.getMessage().equals("Duplicate entry '" + user.getName() + "' for key 'Users.name'")) {
                throw new MyBookListException("User with name \"" + user.getName() + "\" already exists.");
            }
            throw exception;
        }
    }
}
