package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.util.List;

/**
 * The base interface for other DAO interfaces
 * @param <Type> generic type that enables other DAO interfaces to extend a single interface
 */

public interface Dao<Type> {

    /**
     * Method that retrieves an item from the database by matching IDs
     * @param id the ID of the item
     * @return the item found in the database with the matching ID or null for no match found
     * @throws MyBookListException
     */
    Type getById(int id) throws MyBookListException;

    /**
     * Method that adds an item to the database
     * @param item the item that is to be added
     * @return the item used as the parameter but with an assigned ID to it
     * @throws MyBookListException
     */
    Type add(Type item) throws MyBookListException;

    /**
     * Method that updates an existing item in the database
     * @param item the item that is to be updated
     * @throws MyBookListException
     */
    void update(Type item) throws MyBookListException;

    /**
     * Method that deletes an item from the database with the matching ID
     * @param id the ID of the item
     * @throws MyBookListException
     */
    void delete(int id) throws MyBookListException;

    /**
     * Method that retrieves all items from the database of the same type
     * @return a list container of all the items acquired
     * @throws MyBookListException
     */
    List<Type> getAll() throws MyBookListException;

}
