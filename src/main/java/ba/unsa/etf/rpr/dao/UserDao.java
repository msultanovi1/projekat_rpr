package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

/**
 * An interface that implements the base Dao interface and provides additional methods to be implemented
 * by the DaoSqlImpl that works with the User table in the database
 */
public interface UserDao extends Dao<User>{
    /**
     * Method that lets you search the User table by users name
     * @param name of the User
     * @return List of Users that fit the criteria
     */
    List<User> searchByName(String name);

    /**
     * Method that lets you search the User table by users name ans password
     * @param name of the User
     * @param password of the User
     * @return List of Users that fit the criteria
     * @throws MyBookListException
     */
    User searchByNameAndPassword(String name, String password) throws MyBookListException;
}

