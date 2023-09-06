package ba.unsa.etf.rpr.business;


import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

/**
 * Interface that defines main attributes for every manager class
 * @param <Type> generic type tht implements Idable interface
 */
public interface Manager<Type extends Idable> {
    /**
     * Method that return an item from the database that has the same id
     * @param id we search
     * @return item with provided id
    */
    Type getById(int id) throws MyBookListException;

    /**
     * Method that return every element from a given table in a database
     * @return list of elements
     */
    List<Type> getAll() throws MyBookListException;

    /**
     * Method which deletes an element with the given id from the database
     * @param id of the element we want to delete
     */
    void delete(int id) throws MyBookListException;

    /**
     * Method which updates an item in the database
     * @param item we want to update
     */
    void update(Type item) throws MyBookListException;

    /**
     * Method that adds an item to the database
     * @param item we want to add
     */
    void add(Type item) throws MyBookListException;

}
